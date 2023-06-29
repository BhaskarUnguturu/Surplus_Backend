package com.surplus.fwm.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.dto.FoodDto;
import com.surplus.fwm.model.Food;

@Service
public interface IFoodService {

	void addFood(@Valid FoodDto foodDto, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void save(Food food);

	void getAllFoodListDetails(ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getFoodList(ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getFoodById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void distributeFood();

	void getDonationList(ApiResponseDtoBuilder apiResponseDtoBuilder);

	void completedFood();

	void acceptFood(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void rejectFood(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void updateFood(@Valid FoodDto foodDto, long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

}
