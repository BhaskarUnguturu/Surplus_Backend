package com.surplus.fwm.service;

import org.springframework.stereotype.Service;

import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;

@Service
public interface IDashboardService {

	void getAllUserDetails(ApiResponseDtoBuilder apiResponseDtoBuilder);

}
