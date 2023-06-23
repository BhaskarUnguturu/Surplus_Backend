package com.surplus.fwm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.dto.ReportResponseDto;
import com.surplus.fwm.model.Feedback;
import com.surplus.fwm.model.Food;
import com.surplus.fwm.model.Rating;
import com.surplus.fwm.model.User;
import com.surplus.fwm.repository.FeedbackRepository;
import com.surplus.fwm.repository.FoodRepository;
import com.surplus.fwm.repository.RatingRepository;
import com.surplus.fwm.repository.UserRepository;
import com.surplus.fwm.service.IReportService;

@Service
public class ReportServiceImpl implements IReportService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FoodRepository foodRepository;
	@Autowired
	private FeedbackRepository feedbackRepository;
	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public void getAllUserReportDetails(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<ReportResponseDto> allUserDetails = new ArrayList<>();

		List<Food> foodList = foodRepository.findAll();
		for (Food food : foodList) {
			ReportResponseDto reportResponseDto = new ReportResponseDto();
			long userId = food.getUserId();
			Optional<User> userdetails = userRepository.findById(userId);
			List<Feedback> feedbacks = feedbackRepository.findByUserId(userId);
			List<Rating> ratings = ratingRepository.findByUserId(userId);

			reportResponseDto.setUserid(userId);
			reportResponseDto.setAddress(userdetails.get().getAddress());
			reportResponseDto.setEmail(userdetails.get().getEmail());
			reportResponseDto.setFullName(userdetails.get().getFullName());
			reportResponseDto.setMobileNumber(userdetails.get().getMobileNumber());
			reportResponseDto.setFeedback(feedbacks);
			reportResponseDto.setRating(ratings);
			reportResponseDto.setFoods(food);
			allUserDetails.add(reportResponseDto);
		}

		apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(allUserDetails);

	}

}
