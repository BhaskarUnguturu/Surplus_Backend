package com.surplus.fwm.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.mapper.CustomMapper;
import com.surplus.fwm.model.Feedback;
import com.surplus.fwm.model.Food;
import com.surplus.fwm.model.Rating;
import com.surplus.fwm.model.User;
import com.surplus.fwm.repository.FeedbackRepository;
import com.surplus.fwm.repository.FoodRepository;
import com.surplus.fwm.repository.RatingRepository;
import com.surplus.fwm.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {
	@InjectMocks
	ReportServiceImpl reportServiceImpl;
	@Mock
	UserRepository userRepository;
	@Mock
	CustomMapper customMapper;
	@Mock
	private FoodRepository foodRepository;
	@Mock
	private FeedbackRepository feedbackRepository;
	@Mock
	private RatingRepository ratingRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void getAllUserReportDetails() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Food food =new Food();
		food.setId(1l);
		food.setUserId(1l);
		List<Food> foodList = new ArrayList<>();
		foodList.add(food);
		List<Feedback> feedbacks = new ArrayList<Feedback>();
		List<Rating> ratings = new ArrayList<>();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(3);
		when(foodRepository.findAll()).thenReturn(foodList);

		Optional<User> user = Optional.ofNullable(sessionUser);
		when(userRepository.findById(1l)).thenReturn(user);

		when(feedbackRepository.findByUserId(1l)).thenReturn(feedbacks);

		when(ratingRepository.findByUserId(1l)).thenReturn(ratings);

		reportServiceImpl.getAllUserReportDetails(apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}

}
