package com.fwm.service.impl;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fwm.constants.Constants;
import com.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fwm.dto.RatingRequestDto;
import com.fwm.mapper.CustomMapper;
import com.fwm.model.Rating;
import com.fwm.model.User;
import com.fwm.repository.RatingRepository;
import com.fwm.repository.UserRepository;
import com.fwm.service.IRatingService;
import com.fwm.utility.Utility;

@Service
public class RatingServiceImpl implements IRatingService {
	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CustomMapper customMapper;

	@Override
	public void addRating(@Valid RatingRequestDto ratingRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Rating rating = customMapper.ratingRequestDtoToRating(ratingRequestDto);
		rating.setCreatedAt(new Date());
		ratingRepository.save(rating);
		apiResponseDtoBuilder.withMessage(Constants.RATING_ADD_SUCCESS).withStatus(HttpStatus.OK).withData(rating);
	}

	@Override
	public void getRatingListByUserId(long userId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<Rating> ratings = ratingRepository.findByUserId(userId);
		apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(ratings);
	}

}
