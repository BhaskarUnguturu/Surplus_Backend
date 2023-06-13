package com.fwm.service;

import org.springframework.stereotype.Service;

import com.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;

@Service
public interface IDashboardService {

	void getAllUserDetails(ApiResponseDtoBuilder apiResponseDtoBuilder);

}
