package com.surplus.fwm.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.dto.FeedbackRequestDto;
import com.surplus.fwm.model.Feedback;

@Service
public interface IFeedbackService {

	void addFeedback(@Valid FeedbackRequestDto feedbackRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getFeedbackListByUserId(long userId, ApiResponseDtoBuilder apiResponseDtoBuilder);

	Feedback findByFoodId(Long id);

}
