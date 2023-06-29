package com.surplus.fwm.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.surplus.fwm.constants.Constants;
import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.dto.RatingRequestDto;
import com.surplus.fwm.mapper.CustomMapper;
import com.surplus.fwm.model.Rating;
import com.surplus.fwm.model.User;
import com.surplus.fwm.repository.RatingRepository;
import com.surplus.fwm.repository.UserRepository;
import com.surplus.fwm.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class RatingServiceImplTest {
	@InjectMocks
	RatingServiceImpl ratingServiceImpl;
	@Mock
	RatingRepository ratingRepository;
	@Mock
	UserRepository userRepository;
	@Mock
	CustomMapper customMapper;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addRating() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		RatingRequestDto ratingRequestDto = new RatingRequestDto();
		ratingRequestDto.setRating(2);
		Rating rating = new Rating();
		rating.setId(1l);
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		when(customMapper.ratingRequestDtoToRating(ratingRequestDto)).thenReturn(rating);
		ratingServiceImpl.addRating(ratingRequestDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.RATING_ADD_SUCCESS));

	}

	@Test
	public void getRatingListByUserId() {
		long userId = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		RatingRequestDto ratingRequestDto = new RatingRequestDto();
		ratingRequestDto.setRating(2);
		List<Rating> ratings = new ArrayList<>();
		when(ratingRepository.findByUserId(userId)).thenReturn(ratings);
		ratingServiceImpl.getRatingListByUserId(userId, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}
}
