package com.surplus.fwm.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.surplus.fwm.FwmAppApplication;
import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.dto.PaginationDto;
import com.surplus.fwm.dto.UserFilterDto;
import com.surplus.fwm.dto.UserFilterWithPaginationDto;
import com.surplus.fwm.dto.UserRequestDto;
import com.surplus.fwm.model.User;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = FwmAppApplication.class)
public class UserControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private final String URL = "http://localhost:";

	@Test
	public void addUser() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		UserRequestDto userRequestDto = new UserRequestDto();
		userRequestDto.setFullName("Test");
		userRequestDto.setEmail("anonymousUser");
		userRequestDto.setMobileNumber("8888888888");
		userRequestDto.setPassword("Test@123");
		userRequestDto.setAddress("test");

		String url = URL + port + "/api/v1/user/add";
		HttpEntity<UserRequestDto> request = new HttpEntity<>(userRequestDto, headers);

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.postForEntity(url, request,
				ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	public void forgotPassword() throws Exception {
		String url = URL + port + "/api/v1/user/password/forgot";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String email = "test123@test.com";
		HttpEntity<?> entity = new HttpEntity<>(headers);
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("email", "{email}").encode()
				.toUriString();
		Map<String, Object> params = new HashMap<>();
		params.put("email", email);
		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.POST,
				entity, ApiResponseDtoBuilder.class, params);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	 @Test
	public void updateUser() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		User user = new User();
		user.setFullName("Test");
		user.setEmail("anonymousUser");
		user.setMobileNumber("8888888888");
		user.setPassword("Test@123");
		user.setAddress("test");
		user.setId(1L);
		user.setCreatedAt(new Date());
		user.setUpdatedAt(new Date());
		user.setRole(3);

		String url = URL + port + "/api/v1/user/update";
		HttpEntity<User> request = new HttpEntity<>(user, headers);

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.postForEntity(url, request,
				ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	 @Test
	public void getUserDetailsById() throws Exception {
		String url = URL + port + "/api/v1/user/" + 1;

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.getForEntity(url,
				ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	 @Test
	public void isActiveUser() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		long id = 1L;
		boolean active = true;
		String url = URL + port + "/api/v1/user/active";

		HttpEntity<?> entity = new HttpEntity<>(headers);
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("id", "{id}")
				.queryParam("active", "{active}").encode().toUriString();
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		params.put("active", active);

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.POST,
				entity, ApiResponseDtoBuilder.class, params);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	public void deleteUserById() throws Exception {
		String url = URL + port + "/api/v1/user/" + 19 + "/delete";

		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).encode().toUriString();

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.DELETE,
				null, ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	 @Test
	public void referFriendByEmail() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String email = "test123@test.com";
		String url = URL + port + "/api/v1/user/refer";

		HttpEntity<?> entity = new HttpEntity<>(headers);
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("email", "{email}").encode()
				.toUriString();
		Map<String, Object> params = new HashMap<>();
		params.put("email", email);
		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.POST,
				entity, ApiResponseDtoBuilder.class, params);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}
	@Test
	public void changePassword() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String url = URL + port + "/api/v1/changePassword";
		long userId = 1l;
		String oldPassword = "string";
		String newPassword = "string";
		HttpEntity<?> entity = new HttpEntity<>(headers);
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url).queryParam("userId", "{userId}")
				.queryParam("oldPassword", "{oldPassword}").queryParam("newPassword", "{newPassword}").encode()
				.toUriString();
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("oldPassword", oldPassword);
		params.put("newPassword", newPassword);
		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.POST,
				entity, ApiResponseDtoBuilder.class, params);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}
	@Test
	public void getAllUsersListDetails() throws Exception {
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	String url = URL + port + "/api/v1/user/list";
	ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.getForEntity(url,
			ApiResponseDtoBuilder.class);

	assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}
	
	@Test
	public void getUserListByFilterWithPagination() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		UserFilterWithPaginationDto userFilterWithPaginationDto = new UserFilterWithPaginationDto();
		UserFilterDto userFilterDto = new UserFilterDto();
		userFilterDto.setRole(2);
		PaginationDto PaginationDto = new PaginationDto();
		PaginationDto.setCurrentPage(1);
		PaginationDto.setPerPage(10);
		userFilterWithPaginationDto.setPagination(PaginationDto);
		userFilterWithPaginationDto.setFilter(userFilterDto);
		String url = URL + port + "/api/v1/user/pagination/filter";
		HttpEntity<UserFilterWithPaginationDto> request = new HttpEntity<>(userFilterWithPaginationDto, headers);

		ResponseEntity<ApiResponseDtoBuilder> responseEntity = restTemplate.postForEntity(url, request,
				ApiResponseDtoBuilder.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}
	
}