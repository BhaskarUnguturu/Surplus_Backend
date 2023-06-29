package com.surplus.fwm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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
import com.surplus.fwm.repository.custom.FoodRepositoryCustom;
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

	@Autowired
	private FoodRepositoryCustom foodRepositoryCustom;

	@Override
	public void addFood(@Valid FoodDto foodDto, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User user = Utility.getSessionUser(userRepository);
		if (user.getRole() != 5 && user.getRole() != 6) {
			Food food = customMapper.foodDtoToFood(foodDto);
			food.setCreatedAt(new Date());
			food.setUserId(user.getId());
			save(food);
			apiResponseDtoBuilder.withMessage("food add successfully").withStatus(HttpStatus.OK).withData(food);
			new Thread(() -> {
				String subject = "food recived notification";
				String body = "<!DOCTYPE html><html><head><body><p>" + user.getFullName()
						+ ",</p><p>This is notification, Thanks for your donation, system will distribut the donation to best match . </p>";
				emailService.sendEmail(user.getEmail(), subject, body, "", null, null);
			}).start();
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
	public void getFoodList(ApiResponseDtoBuilder apiResponseDtoBuilder) {
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

	@Override
	public void getDonationList(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User user = Utility.getSessionUser(userRepository);
		List<FoodResponseDto> dataList = new ArrayList<>();
		if (user.getRole() == 0) {
			List<Food> foodList = foodRepository.findAll();
			fillFoodResponseDto(dataList, foodList);
		} else if (user.getRole() == 1 || user.getRole() == 2 || user.getRole() == 3 || user.getRole() == 4) {
			List<Food> foodList = foodRepository.findByUserId(user.getId());
			fillFoodResponseDto(dataList, foodList);
		} else if (user.getRole() == 5 || user.getRole() == 6) {
			List<Food> foodList = foodRepository.findByStatus(0);
			fillFoodResponseDto(dataList, foodList);
		}
		apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(dataList);
	}

	private void fillFoodResponseDto(List<FoodResponseDto> dataList, List<Food> foodList) {
		for (Food food : foodList) {
			FoodResponseDto foodResponse = customMapper.foodToFoodResponse(food);
			foodResponse.setDonorName(userService.findById(food.getUserId()).getFullName());
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
	public void distributeFood() {
		List<Food> donatedFoodlist = foodRepositoryCustom.getLast24HourDonatedFood();
		List<Long> userList = new ArrayList<>();
		for (Food food : donatedFoodlist) {
			if (!userList.contains(food.getReceipentId())) {
				userList.add(food.getReceipentId());
			}
		}
		List<Integer> roleList = new ArrayList<>();
		roleList.add(5);
		roleList.add(6);
		List<User> receipents = new ArrayList<>();
		if (userList.isEmpty()) {
			receipents = userRepository.findByRoleIn(roleList);
		} else {
			receipents = userRepository.findByRoleInAndIdNotIn(roleList, userList);
		}
		List<Food> pendingFoods = foodRepository.findByStatus(0);
		for (Food food : pendingFoods) {
			List<User> matchedUserList = new ArrayList<>();
			for (User user : receipents) {
				if (user.getDietaryRestrictions() != null && food.getDietaryRestrictions() != null) {
					if (isContain(user.getDietaryRestrictions(), food.getDietaryRestrictions())) {
						if (food.getTypeOfDonation().equals("indivisual") && user.getRole() == 5) {
							matchedUserList.add(user);
						} else if (food.getTypeOfDonation().equals("organization") && user.getRole() == 6) {
							matchedUserList.add(user);
						}
					}
				}
			}
			if (!matchedUserList.isEmpty()) {
				Random rand = new Random();
				User randomUser = matchedUserList.get(rand.nextInt(matchedUserList.size()));
				food.setReceipentId(randomUser.getId());
				food.setDistributionDate(new Date());
			}
			food.setStatus(1);
			foodRepository.save(food);
		}
	}

	@Override
	public void completedFood() {
		List<Food> inProgressFoods = foodRepository.findByStatus(2);
		for (Food food : inProgressFoods) {
			food.setStatus(3);
			foodRepository.save(food);
			if (food.getReceipentId() != null && userRepository.existsById(food.getUserId())
					&& userRepository.existsById(food.getReceipentId())) {
				sendEmailToDoner(userRepository.findById(food.getUserId()).get(),
						userRepository.findById(food.getReceipentId()).get());
				sendEmailToRecipient(userRepository.findById(food.getUserId()).get(),
						userRepository.findById(food.getReceipentId()).get());
			}
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

	@Override
	public void acceptFood(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Food> food = foodRepository.findById(id);
		if (food.isPresent()) {
			food.get().setStatus(2);
			save(food.get());
			apiResponseDtoBuilder.withMessage("Request accepted").withStatus(HttpStatus.OK).withData(food);
		} else {
			apiResponseDtoBuilder.withMessage("fail").withStatus(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void rejectFood(long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Food> food = foodRepository.findById(id);
		if (food.isPresent()) {
			food.get().setStatus(0);
			food.get().setReceipentId(null);
			save(food.get());
			apiResponseDtoBuilder.withMessage("Request rejected").withStatus(HttpStatus.OK).withData(food);
		} else {
			apiResponseDtoBuilder.withMessage("fail").withStatus(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void updateFood(@Valid FoodDto foodDto, long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Food> food = foodRepository.findById(id);
		if (food.isPresent()) {
			food.get().setTypeOfFood(foodDto.getTypeOfFood());
			food.get().setQuantity(foodDto.getQuantity());
			food.get().setTypeOfDonation(foodDto.getTypeOfDonation());
			food.get().setExpirationDate(foodDto.getExpirationDate());
			food.get().setDietaryRestrictions(foodDto.getDietaryRestrictions());
			food.get().setSchedulingTimeDate(foodDto.getSchedulingTimeDate());
			food.get().setVenue(foodDto.getVenue());
			food.get().setUpdatedAt(new Date());
			save(food.get());
			apiResponseDtoBuilder.withMessage("Donation updated").withStatus(HttpStatus.OK).withData(food);
		} else {
			apiResponseDtoBuilder.withMessage("fail").withStatus(HttpStatus.NOT_FOUND);
		}
	}
}
