package com.fwm.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fwm.dto.RatingRequestDto;

@Service
public interface IRatingService {

	void addRating(@Valid RatingRequestDto ratingRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getRatingListByUserId(long userId, ApiResponseDtoBuilder apiResponseDtoBuilder);

}
