package com.surplus.fwm.service.impl;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.dto.ContactRequestDto;
import com.surplus.fwm.mapper.CustomMapper;
import com.surplus.fwm.model.Contact;
import com.surplus.fwm.repository.ContactRepository;
import com.surplus.fwm.service.IContactService;

@Service
public class ContactServiceImpl implements IContactService {
	@Autowired
	private CustomMapper customMapper;

	@Autowired
	private ContactRepository contactRepository;

	@Override
	public void addContact(@Valid ContactRequestDto contactRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Contact contact = customMapper.contactRequestDtoToContact(contactRequestDto);
		contact.setCreatedAt(new Date());
		save(contact);
		apiResponseDtoBuilder.withMessage("Your query sent to admin.").withStatus(HttpStatus.OK);
	}

	private void save(Contact contact) {
		contactRepository.save(contact);
	}

	@Override
	public void getAllContact(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<Contact> dataList = contactRepository.findAll();
		apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(dataList);
	}
}
