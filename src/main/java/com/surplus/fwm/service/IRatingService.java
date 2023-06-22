package com.surplus.fwm.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.dto.RatingRequestDto;
import com.surplus.fwm.model.Rating;

@Service
public interface IRatingService {

	void addRating(@Valid RatingRequestDto ratingRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getRatingListByUserId(long userId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	Rating getRatingByFoodId(Long id);

}
