package com.surplus.fwm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.surplus.fwm.constants.Constants;
import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.dto.FoodDto;
import com.surplus.fwm.dto.FoodResponseDto;
import com.surplus.fwm.mapper.CustomMapper;
import com.surplus.fwm.model.Feedback;
import com.surplus.fwm.model.Food;
import com.surplus.fwm.model.Rating;
import com.surplus.fwm.model.User;
import com.surplus.fwm.repository.FoodRepository;
import com.surplus.fwm.repository.UserRepository;
import com.surplus.fwm.service.IEmailService;
import com.surplus.fwm.service.IFeedbackService;
import com.surplus.fwm.service.IFoodService;
import com.surplus.fwm.service.IRatingService;
import com.surplus.fwm.service.IUserService;
import com.surplus.fwm.utility.Utility;

@Service
public class FoodServiceImpl implements IFoodService {
	@Autowired
	private CustomMapper customMapper;
	@Autowired
	private FoodRepository foodRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private IEmailService emailService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IRatingService ratingService;

	@Autowired
	private IFeedbackService feedbackService;

	@Override
	public void addFood(@Valid FoodDto foodDto, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User user = Utility.getSessionUser(userRepository);
		if (user.getRole() != 5 && user.getRole() != 6) {
			Food food = customMapper.foodDtoToFood(foodDto);
			food.setCreatedAt(new Date());
			food.setUserId(user.getId());
			save(food);
			apiResponseDtoBuilder.withMessage("food add successfully").withStatus(HttpStatus.OK).withData(food);
			String subject = "food recived notification";
			String body = "<!DOCTYPE html><html><head><body><p>" + user.getFullName()
					+ ",</p><p>This is notification, Thanks for your donation, system will distribut the donation to best match . </p>";
			emailService.sendEmail(user.getEmail(), subject, body, "", null, null);

		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.OK);
		}
	}

	@Override
	public void save(Food food) {
		foodRepository.save(food);
	}

	@Override
	public void getAllFoodListDetails(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<Food> foodList = foodRepository.findAll();
		apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(foodList);
	}

	@Override
	public void getFoodListByUserId(long userId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User user = Utility.getSessionUser(userRepository);
		List<FoodResponseDto> dataList = new ArrayList<>();
		if (user.getRole() == 0) {
			List<Food> foodList = foodRepository.findAll();
			fillFoodResponseDto(dataList, foodList);
		} else if (user.getRole() == 1 || user.getRole() == 2 || user.getRole() == 3 || user.getRole() == 4) {
			List<Food> foodList = foodRepository.findByUserId(user.getId());
			fillFoodResponseDto(dataList, foodList);
		} else if (user.getRole() == 5 || user.getRole() == 6) {
			List<Food> foodList = foodRepository.findByReceipentId(user.getId());
			fillFoodResponseDto(dataList, foodList);
		}
		apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(dataList);
	}

	private void fillFoodResponseDto(List<FoodResponseDto> dataList, List<Food> foodList) {
		for (Food food : foodList) {
			FoodResponseDto foodResponse = customMapper.foodToFoodResponse(food);
			Rating rating = ratingService.getRatingByFoodId(food.getId());
			if (rating != null) {
				foodResponse.setRating(rating.getRating());
			}
			Feedback feedback = feedbackService.findByFoodId(food.getId());
			if (feedback != null) {
				foodResponse.setFeedback(feedback.getFeedback());
			}
			if (food.getReceipentId() != null) {
				foodResponse.setReceipentName(userService.findById(food.getReceipentId()).getFullName());
			}
			dataList.add(foodResponse);
		}
	}

	@Override
	public void getFoodById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Food> food = foodRepository.findById(id);
		if (food.isPresent()) {
			apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(food);
		} else {
			apiResponseDtoBuilder.withMessage("fail").withStatus(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public void deliverFood() {
		List<Food> inProgressFoods = foodRepository.findByStatus(1);
		for (Food food : inProgressFoods) {
			food.setStatus(2);
			foodRepository.save(food);
			if (food.getReceipentId() != null && userRepository.existsById(food.getUserId())
					&& userRepository.existsById(food.getReceipentId())) {
				sendEmailToDoner(userRepository.getById(food.getUserId()),
						userRepository.getById(food.getReceipentId()));
				sendEmailToRecipient(userRepository.getById(food.getUserId()),
						userRepository.getById(food.getReceipentId()));
			}
		}
		List<Integer> roleList = new ArrayList<>();
		roleList.add(5);
		roleList.add(6);
		List<User> receipents = userRepository.findByRoleIn(roleList);
		List<Food> pendingFoods = foodRepository.findByStatus(0);
		for (Food food : pendingFoods) {
			for (User user : receipents) {
				if (user.getDietaryRestrictions() != null && food.getDietaryRestrictions() != null) {
					if (isContain(user.getDietaryRestrictions(), food.getDietaryRestrictions())) {
						food.setReceipentId(user.getId());
						break;
					}
				}
			}
			food.setStatus(1);
			foodRepository.save(food);
		}
	}

	private void sendEmailToDoner(User doner, User Recipient) {
		try {
			new Thread(() -> {
				String subject = "event update notification";
				String body = "<!DOCTYPE html><html><body><p>your donation has been distributed successfully to "
						+ Recipient.getFullName() + "</p></body></html>";
				emailService.sendEmail(doner.getEmail(), subject, body, "", null, null);
			}).start();
		} catch (Exception e) {
			System.out.println("sendEmailToDoner:" + e.getMessage());
		}

	}

	private void sendEmailToRecipient(User doner, User Recipient) {
		try {
			new Thread(() -> {
				String subject = "event update notification";
				String body = "<!DOCTYPE html><html><body><p>Congrats," + doner.getFullName()
						+ " has donated food for you.</p></body></html>";
				emailService.sendEmail(Recipient.getEmail(), subject, body, "", null, null);
			}).start();
		} catch (Exception e) {
			System.out.println("sendEmailToDoner:" + e.getMessage());
		}

	}

	private static boolean isContain(String source, String subItem) {
		String pattern = "\\b" + subItem + "\\b";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);
		return m.find();
	}
}
