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

	void getFoodListByUserId(long userId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getFoodById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void deliverFood();

}
