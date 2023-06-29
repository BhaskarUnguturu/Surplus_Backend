package com.surplus.fwm.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.surplus.fwm.constants.Constants;
import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.dto.PaginationDto;
import com.surplus.fwm.dto.UserFilterDto;
import com.surplus.fwm.dto.UserFilterWithPaginationDto;
import com.surplus.fwm.dto.UserRequestDto;
import com.surplus.fwm.mapper.CustomMapper;
import com.surplus.fwm.model.User;
import com.surplus.fwm.repository.UserRepository;
import com.surplus.fwm.repository.custom.UserRepositoryCustom;
import com.surplus.fwm.service.IEmailService;
import com.surplus.fwm.service.IVerificationTokenService;
import com.surplus.fwm.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	@InjectMocks
	UserServiceImpl userServiceImpl;
	@Mock
	UserRepository userRepository;
	@Mock
	CustomMapper customMapper;
	@Mock
	IEmailService emailService;
	@Mock
	IVerificationTokenService verificationTokenService;
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Mock
	UserRepositoryCustom userRepositoryCustom;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addUser() {
		UserRequestDto userRequestDto = new UserRequestDto();
		userRequestDto.setFullName("Test Admin");
		userRequestDto.setEmail("anonymousUser");
		userRequestDto.setMobileNumber("9999999999");
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		HttpServletRequest request = null;
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);

		when(customMapper.userRequestDtoToUser(userRequestDto)).thenReturn(sessionUser);
		userServiceImpl.addUser(userRequestDto, apiResponseDtoBuilder, request);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.USER_ADD_SUCCESS));

	}

	@Test
	public void deleteUserById() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(0);
		Optional<User> customers = Optional.ofNullable(sessionUser);
		when(userRepository.findById(id)).thenReturn(customers);
		userServiceImpl.deleteUserById(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.USER_DELETED_SUCCESSFULLY));

	}

	@Test
	public void getUserListByFilterWithPagination() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();

		UserFilterWithPaginationDto UserFilterWithPaginationDto = new UserFilterWithPaginationDto();
		UserFilterDto userFilterDto = new UserFilterDto();
		userFilterDto.setKeyword("test");
		userFilterDto.setRole(1);
		UserFilterWithPaginationDto.setFilter(userFilterDto);

		PaginationDto paginationDto = new PaginationDto();
		paginationDto.setCurrentPage(1);
		paginationDto.setPerPage(5);
		paginationDto.setTotalCount(23);
		paginationDto.setTotalPages(30);

		UserFilterWithPaginationDto.setPagination(paginationDto);
		when(userRepositoryCustom.getUserListByFilterWithPagination(UserFilterWithPaginationDto))
				.thenReturn(paginationDto);
		userServiceImpl.getUserListByFilterWithPagination(UserFilterWithPaginationDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.DATA_LIST));
	}

	@Test
	public void isActiveUser() {
		long id = 1L;
		boolean active = true;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User user = new User();
		user.setFullName("Test Admin");
		user.setEmail("testadmin@gmail.com");
		user.setMobileNumber("9999999999");
		user.setRole(1);
		Optional<User> userDb = Optional.of(user);
		when(userRepository.findById(id)).thenReturn(userDb);
		userServiceImpl.isActiveUser(id, active, apiResponseDtoBuilder);
		assertTrue(userDb.isPresent());

	}

	@Test
	public void sendTemporaryPassword() {
		String email = "test";
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User user = new User();
		when(userRepository.findByEmail(email)).thenReturn(user);
		userServiceImpl.sendTemporaryPassword(email, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SEND_DETAILS_TO_YOUR_EMAIL));
	}

	@Test
	public void getAllUserDetails() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		List<User> userList = new ArrayList<>();
		when(userRepository.findAll()).thenReturn(userList);
		userServiceImpl.getAllUserDetails(apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}

	@Test
	public void updateUser() {
		User user = new User();
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		user.setEmail("string");
		user.setFullName("string");
		user.setMobileNumber("string");
		user.setPassword("string");
		userServiceImpl.updateUser(user, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Not found"));

	}

	@Test
	public void getUserDetailsById() {
		long id = 1l;
		User user = new User();
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		user.setEmail("test");
		user.setFullName("test");
		user.setMobileNumber("123456789");
		user.setPassword("test");
		Optional<User> employees = Optional.ofNullable(user);
		when(userRepository.findById(id)).thenReturn(employees);
		userServiceImpl.getUserDetailsById(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}

	@Test
	public void referFriendByEmail() {
		User user = new User();
		String email = "test123@gmail.com";
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		user.setEmail("test");
		user.setFullName("test");
		user.setMobileNumber("123456789");
		user.setPassword("test");
		when(Utility.getSessionUser(userRepository)).thenReturn(user);
		userServiceImpl.referFriendByEmail(email, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Successfully refered!!"));

	}

	@Test
	public void changePassword() {
		long userId = 1;
		String oldPassword = "string";
		String newPassword = "string";
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		Optional<User> userValue = Optional.ofNullable(sessionUser);
		when(userRepository.findById(userId)).thenReturn(userValue);
		userServiceImpl.changePassword(userId, oldPassword, newPassword, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Old Password Is Worng "));

	}

}
