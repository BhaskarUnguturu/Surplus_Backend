package com.surplus.fwm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.surplus.fwm.constants.Constants;
import com.surplus.fwm.dto.ApiResponseDto;
import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.dto.UserFilterWithPaginationDto;
import com.surplus.fwm.dto.UserRequestDto;
import com.surplus.fwm.model.User;
import com.surplus.fwm.service.IUserService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class UserController {

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/user/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto addUser(@Valid @RequestBody UserRequestDto userRequestDto, final HttpServletRequest request) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.addUser(userRequestDto, apiResponseDtoBuilder, request);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/user/password/forgot", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto forgotPassword(@RequestParam(required = true) String email) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.sendTemporaryPassword(email, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getUserDetailsById(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.getUserDetailsById(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@RequestMapping(value = "/changePassword", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ApiResponseDto changePassword(@RequestParam long userId, @RequestParam String oldPassword,
			@RequestParam String newPassword) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		userService.changePassword(userId, oldPassword, newPassword, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	
}
