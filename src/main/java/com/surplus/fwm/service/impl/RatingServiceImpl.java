package com.surplus.fwm.service.impl;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.surplus.fwm.constants.Constants;
import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.dto.RatingRequestDto;
import com.surplus.fwm.mapper.CustomMapper;
import com.surplus.fwm.model.Rating;
import com.surplus.fwm.model.User;
import com.surplus.fwm.repository.RatingRepository;
import com.surplus.fwm.repository.UserRepository;
import com.surplus.fwm.service.IRatingService;
import com.surplus.fwm.utility.Utility;

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
		User user = Utility.getSessionUser(userRepository);
		Rating rating = customMapper.ratingRequestDtoToRating(ratingRequestDto);
		rating.setUserId(user.getId());
		rating.setCreatedAt(new Date());
		ratingRepository.save(rating);
		apiResponseDtoBuilder.withMessage(Constants.RATING_ADD_SUCCESS).withStatus(HttpStatus.OK).withData(rating);
	}

	@Override
	public void getRatingListByUserId(long userId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<Rating> ratings = ratingRepository.findByUserId(userId);
		apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(ratings);
	}

	@Override
	public Rating getRatingByFoodId(Long id) {
		return ratingRepository.findByFoodId(id);
	}

}
