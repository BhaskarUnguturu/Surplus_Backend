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
import com.surplus.fwm.dto.FeedbackRequestDto;
import com.surplus.fwm.mapper.CustomMapper;
import com.surplus.fwm.model.Feedback;
import com.surplus.fwm.model.User;
import com.surplus.fwm.repository.FeedbackRepository;
import com.surplus.fwm.repository.UserRepository;
import com.surplus.fwm.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceImplTest {
	@InjectMocks
	FeedbackServiceImpl feedbackServiceImpl;
	@Mock
	UserRepository userRepository;
	@Mock
	FeedbackRepository feedbackRepository;
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
	public void addFeedback() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		FeedbackRequestDto feedbackRequestDto = new FeedbackRequestDto();
		feedbackRequestDto.setFeedback("test");
		Feedback feedback = new Feedback();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		when(customMapper.feedbackRequestDtoToFeedback(feedbackRequestDto)).thenReturn(feedback);
		feedbackServiceImpl.addFeedback(feedbackRequestDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.FEEDBACK_SENT_SUCCESSFULLY));

	}

	@Test
	public void getFeedbackListByUserId() {
		long userId = 1L;
		List<Feedback> feedbacks = new ArrayList<Feedback>();
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		when(feedbackRepository.findByUserId(userId)).thenReturn(feedbacks);
		feedbackServiceImpl.getFeedbackListByUserId(userId, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}

}
