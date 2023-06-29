package com.surplus.fwm.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.surplus.fwm.FwmAppApplication;
import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.dto.ContactRequestDto;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = FwmAppApplication.class)
public class ContactControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private final String URL = "http://localhost:";

	@Test
	public void addContact() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ContactRequestDto contactRequestDto = new ContactRequestDto();
		contactRequestDto.setMessage("test");
		contactRequestDto.setEmail("test@123.com");
		contactRequestDto.setName("test");
		String url = URL + port + "/api/v1/contact/add";
		HttpEntity<ContactRequestDto> request = new HttpEntity<>(contactRequestDto, headers);

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.postForEntity(url, request,
				ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}
	@Test
	public void getAllContact() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String url = URL + port + "/api/v1/contact/get/all";
		HttpEntity<String> request = new HttpEntity<>(headers);

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.getForEntity(url, ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

}
