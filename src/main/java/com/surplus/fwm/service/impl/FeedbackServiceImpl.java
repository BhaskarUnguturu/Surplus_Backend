package com.surplus.fwm.service.impl;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.surplus.fwm.constants.Constants;
import com.surplus.fwm.dto.FeedbackRequestDto;
import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.mapper.CustomMapper;
import com.surplus.fwm.model.Feedback;
import com.surplus.fwm.repository.FeedbackRepository;
import com.surplus.fwm.repository.UserRepository;
import com.surplus.fwm.service.IFeedbackService;

@Service
public class FeedbackServiceImpl implements IFeedbackService {
	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private CustomMapper customMapper;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void addFeedback(@Valid FeedbackRequestDto feedbackRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Feedback feedback = customMapper.feedbackRequestDtoToFeedback(feedbackRequestDto);
		feedback.setCreatedAt(new Date());
		feedbackRepository.save(feedback);
		apiResponseDtoBuilder.withMessage(Constants.FEEDBACK_SENT_SUCCESSFULLY).withStatus(HttpStatus.OK)
				.withData(feedback);
	}

	@Override
	public void getFeedbackListByUserId(long userId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<Feedback> feedbacks = feedbackRepository.findByUserId(userId);
		apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(feedbacks);
	}

}
