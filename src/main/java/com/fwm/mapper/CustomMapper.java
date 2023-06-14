package com.fwm.mapper;

import java.util.List;

import javax.validation.Valid;

import org.mapstruct.Mapper;

import com.fwm.dto.FeedbackRequestDto;
import com.fwm.dto.FoodDto;
import com.fwm.dto.RatingRequestDto;
import com.fwm.dto.UserListResponseDto;
import com.fwm.dto.UserRequestDto;
import com.fwm.model.Feedback;
import com.fwm.model.Food;
import com.fwm.model.Rating;
import com.fwm.model.User;

@Mapper(componentModel = "spring")
public interface CustomMapper {

	User userRequestDtoToUser(UserRequestDto userRequestDto);

	Food foodDtoToFood(@Valid FoodDto foodDto);

	Feedback feedbackRequestDtoToFeedback(@Valid FeedbackRequestDto feedbackRequestDto);

	Rating ratingRequestDtoToRating(@Valid RatingRequestDto ratingRequestDto);

	List<UserListResponseDto> userListToUserListResponseDtoList(List<User> userList);


}