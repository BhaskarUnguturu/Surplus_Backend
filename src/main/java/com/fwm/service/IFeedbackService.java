package com.fwm.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fwm.dto.FeedbackRequestDto;

@Service
public interface IFeedbackService {

	void addFeedback(@Valid FeedbackRequestDto feedbackRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getFeedbackListByUserId(long userId, ApiResponseDtoBuilder apiResponseDtoBuilder);

}
