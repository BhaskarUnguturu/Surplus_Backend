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

import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.dto.FoodDto;
import com.surplus.fwm.mapper.CustomMapper;
import com.surplus.fwm.model.Food;
import com.surplus.fwm.model.User;
import com.surplus.fwm.repository.FoodRepository;
import com.surplus.fwm.repository.UserRepository;
import com.surplus.fwm.service.IEmailService;
import com.surplus.fwm.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class FoodServiceImplTest {
	@InjectMocks
	FoodServiceImpl foodServiceImpl;
	@Mock
	UserRepository userRepository;
	@Mock
	FoodRepository foodRepository;
	@Mock
	CustomMapper customMapper;
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Mock
	private IEmailService emailService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addFood() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		FoodDto foodDto = new FoodDto();
		foodDto.setTypeOfDonation("test");
		Food food = new Food();
		food.setUserId(1l);
		when(customMapper.foodDtoToFood(foodDto)).thenReturn(food);
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(2);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		foodServiceImpl.addFood(foodDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("food add successfully"));

	}@Test
	public void updateFood() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		FoodDto foodDto = new FoodDto();
		foodDto.setTypeOfDonation("test");
		Food food = new Food();
		food.setUserId(1l);
		Optional<Food> foods =Optional.of(food);
		when(foodRepository.findById(1l)).thenReturn(foods);
		foodServiceImpl.updateFood(foodDto,1l, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Donation updated"));

	}

	@Test
	public void getAllFoodListDetails() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		List<Food> foodList = new ArrayList<>();
		Food food = new Food();
		food.setTypeOfDonation("test");
		foodList.add(food);
		when(foodRepository.findAll()).thenReturn(foodList);
		foodServiceImpl.getAllFoodListDetails(apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));
	}

	@Test
	public void getFoodListByUserId() {
		long userId = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		List<Food> foodList = new ArrayList<>();
		Food food = new Food();
		food.setTypeOfDonation("test");
		foodList.add(food);
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(10);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		foodServiceImpl.getFoodList(apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}

	@Test
	public void getFoodById() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Food food = new Food();
		food.setTypeOfDonation("test");
		Optional<Food> user = Optional.ofNullable(food);
		when(foodRepository.findById(id)).thenReturn(user);
		foodServiceImpl.getFoodById(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));

	}
	
	@Test
	public void getDonationList() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		List<Food> foodList = new ArrayList<>();
		Food food = new Food();
		food.setTypeOfDonation("test");
		foodList.add(food);
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(7);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		foodServiceImpl.getDonationList(apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("success"));
	}
	
	@Test
	public void acceptFood() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Food food = new Food();
		food.setTypeOfDonation("test");
		Optional<Food> user = Optional.ofNullable(food);
		when(foodRepository.findById(id)).thenReturn(user);
		foodServiceImpl.acceptFood(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Request accepted"));

	}

	
	
	@Test
	public void rejectFood() {
		long id = 1l;
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		Food food = new Food();
		food.setTypeOfDonation("test");
		Optional<Food> user = Optional.ofNullable(food);
		when(foodRepository.findById(id)).thenReturn(user);
		foodServiceImpl.rejectFood(id, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals("Request rejected"));

	}
}
