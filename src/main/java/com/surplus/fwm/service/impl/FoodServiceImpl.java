package com.surplus.fwm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.surplus.fwm.dto.FoodDto;
import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.mapper.CustomMapper;
import com.surplus.fwm.model.Food;
import com.surplus.fwm.model.User;
import com.surplus.fwm.repository.FoodRepository;
import com.surplus.fwm.repository.UserRepository;
import com.surplus.fwm.service.IEmailService;
import com.surplus.fwm.service.IFoodService;
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

	@Override
	public void addFood(@Valid FoodDto foodDto, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Food food = customMapper.foodDtoToFood(foodDto);
		save(food);

		apiResponseDtoBuilder.withMessage("food add successfully").withStatus(HttpStatus.OK).withData(food);	}

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
		List<Food> foodList = foodRepository.findByUserId(userId);
		apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(foodList);
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
		}
		List<Integer> roleList = new ArrayList<>();
		roleList.add(5);
		roleList.add(6);
		List<User> receipents = userRepository.findByRoleIn(roleList);
		List<Food> pendingFoods = foodRepository.findByStatus(0);
		for (Food food : pendingFoods) {
			for (User user : receipents) {
				if (isContain(user.getDietaryRestrictions(), food.getDietaryRestrictions())) {
					food.setReceipentId(user.getId());
					break;
				}
			}
			food.setStatus(1);
			foodRepository.save(food);
		}
	}

	private static boolean isContain(String source, String subItem) {
		String pattern = "\\b" + subItem + "\\b";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);
		return m.find();
	}
}
