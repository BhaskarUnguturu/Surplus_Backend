package com.fwm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fwm.constants.Constants;
import com.fwm.dto.ApiResponseDto;
import com.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fwm.service.IDashboardService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class DashboardController {
	@Autowired
	private IDashboardService dashboardService;

	@RequestMapping(value = "/dashboard", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ApiResponseDto getAllUsersListDetails() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		dashboardService.getAllUserDetails(apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
}
