package com.surplus.fwm.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.dto.ContactRequestDto;

@Service
public interface IContactService {

	void addContact(@Valid ContactRequestDto contactRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder);

	void getAllContact(ApiResponseDtoBuilder apiResponseDtoBuilder);

}
