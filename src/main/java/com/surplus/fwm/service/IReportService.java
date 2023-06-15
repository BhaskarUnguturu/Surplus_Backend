package com.surplus.fwm.service;

import org.springframework.stereotype.Service;

import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;

@Service
public interface IReportService {

	void getAllUserReportDetails(ApiResponseDtoBuilder apiResponseDtoBuilder);

}
