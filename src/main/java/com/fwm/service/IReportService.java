package com.fwm.service;

import org.springframework.stereotype.Service;

import com.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;

@Service
public interface IReportService {

	void getAllUserReportDetails(ApiResponseDtoBuilder apiResponseDtoBuilder);

}
