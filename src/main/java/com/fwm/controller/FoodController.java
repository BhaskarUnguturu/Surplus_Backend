package com.fwm.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fwm.constants.Constants;
import com.fwm.dto.ApiResponseDto;
import com.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fwm.dto.FoodDto;
import com.fwm.service.IFoodService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class FoodController {
	@Autowired
	private IFoodService foodService;

	@RequestMapping(value = "/food/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto addFood(@Valid @RequestBody FoodDto foodDto) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		foodService.addFood(foodDto, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
	@RequestMapping(value = "/getAll/food/list", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getAllFoodListDetails() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		foodService.getAllFoodListDetails(apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
	@RequestMapping(value = "/food/list/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getFoodListByUserId(@PathVariable(required = true) long userId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		foodService.getFoodListByUserId(userId, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
	@RequestMapping(value = "/get/food/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getFoodById(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		foodService.getFoodById(id,apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
}
