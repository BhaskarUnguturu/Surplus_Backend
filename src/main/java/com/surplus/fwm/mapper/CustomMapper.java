package com.surplus.fwm.mapper;

import java.util.List;

import javax.validation.Valid;

import org.mapstruct.Mapper;

import com.surplus.fwm.dto.ContactRequestDto;
import com.surplus.fwm.dto.FeedbackRequestDto;
import com.surplus.fwm.dto.FoodDto;
import com.surplus.fwm.dto.FoodResponseDto;
import com.surplus.fwm.dto.RatingRequestDto;
import com.surplus.fwm.dto.UserListResponseDto;
import com.surplus.fwm.dto.UserRequestDto;
import com.surplus.fwm.model.Contact;
import com.surplus.fwm.model.Feedback;
import com.surplus.fwm.model.Food;
import com.surplus.fwm.model.Rating;
import com.surplus.fwm.model.User;

@Mapper(componentModel = "spring")
public interface CustomMapper {

	User userRequestDtoToUser(UserRequestDto userRequestDto);

	Food foodDtoToFood(@Valid FoodDto foodDto);

	Feedback feedbackRequestDtoToFeedback(@Valid FeedbackRequestDto feedbackRequestDto);

	Rating ratingRequestDtoToRating(@Valid RatingRequestDto ratingRequestDto);

	List<UserListResponseDto> userListToUserListResponseDtoList(List<User> userList);

	FoodResponseDto foodToFoodResponse(Food food);

	Contact contactRequestDtoToContact(@Valid ContactRequestDto contactRequestDto);


}