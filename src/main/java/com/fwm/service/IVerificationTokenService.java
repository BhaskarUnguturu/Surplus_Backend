package com.fwm.service;

import org.springframework.stereotype.Service;

import com.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fwm.model.User;

@Service
public interface IVerificationTokenService {

	String validateToken(String token);

	void resendRegistrationToken(Long id, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void sendVerificationToken(User user);

}
