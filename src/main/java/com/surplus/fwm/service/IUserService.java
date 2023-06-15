package com.surplus.fwm.service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.surplus.fwm.dto.UserFilterWithPaginationDto;
import com.surplus.fwm.dto.UserRequestDto;
import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.model.User;

@Service
public interface IUserService {

	void addUser(UserRequestDto userRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder,
			HttpServletRequest request);

	User findByMobileNumber(String username);

	User findByMobileNumberOrEmail(String username, String username2);

	void save(User checkUser);

	User findById(Long id);

	void sendTemporaryPassword(String username, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void deleteUserById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	User getSessionUser();

	User findByMobileNumberOrEmailAndPassword(String username, String username2, String password);


	User findByEmail(String email);

	void changePassword(long userId, String oldPassword, String newPassword,
			ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getAllUserDetails(ApiResponseDtoBuilder apiResponseDtoBuilder);

	void isActiveUser(long id, boolean active, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getUserListByFilterWithPagination(UserFilterWithPaginationDto filterWithPagination,
			ApiResponseDtoBuilder apiResponseDtoBuilder);

	void updateUser(@Valid User user, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getUserDetailsById(long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void referFriendByEmail(String email, ApiResponseDtoBuilder apiResponseDtoBuilder);

}
