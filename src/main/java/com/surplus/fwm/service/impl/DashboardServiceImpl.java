package com.surplus.fwm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.surplus.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.surplus.fwm.dto.DashboardResponseDto;
import com.surplus.fwm.model.Food;
import com.surplus.fwm.model.User;
import com.surplus.fwm.repository.FoodRepository;
import com.surplus.fwm.repository.UserRepository;
import com.surplus.fwm.service.IDashboardService;

@Service
public class DashboardServiceImpl implements IDashboardService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FoodRepository foodRepository;

	@Override
	public void getAllUserDetails(ApiResponseDtoBuilder apiResponseDtoBuilder) {
		DashboardResponseDto dashboardResponseDto = new DashboardResponseDto();
		// 1-university
		List<User> ListOfUniversityMember = userRepository.findByRole(1);
		dashboardResponseDto.setTotalCountOfUniversityMember(ListOfUniversityMember.size());
		// 2-restaurant
		List<User> ListOfRestaurant = userRepository.findByRole(2);
		dashboardResponseDto.setTotalCountOfRestaurantMember(ListOfRestaurant.size());
		// 3-individual
		List<User> ListOfIndividual = userRepository.findByRole(3);
		dashboardResponseDto.setTotalCountOfIndividualMember(ListOfIndividual.size());
		// 4-NGO
		List<User> ListOfNGO = userRepository.findByRole(4);
		dashboardResponseDto.setTotalCountOfNGO(ListOfNGO.size());
		// 5-Recipient_individual
		List<User> ListOfRecipientIndividual = userRepository.findByRole(5);
		dashboardResponseDto.setTotalCountOfRecipientIndividualMember(ListOfRecipientIndividual.size());
		// 6-Recipient_organization
		List<User> ListOfRecipientOrganization = userRepository.findByRole(6);
		dashboardResponseDto.setTotalCountOfRecipientOrganizationMember(ListOfRecipientOrganization.size());
		// food in pending
		List<Food> ListOfFoodInPending = foodRepository.findByStatus(0);
		dashboardResponseDto.setTotalCountOfFoodPending(ListOfFoodInPending.size());
		// food in Progress
		List<Food> ListOfFoodInProgress = foodRepository.findByStatus(1);
		dashboardResponseDto.setTotalCountOfFoodInProgress(ListOfFoodInProgress.size());
		// food in donat
		List<Food> ListOfFoodDonate = foodRepository.findByStatus(0);
		dashboardResponseDto.setTotalCountOfDonateFood(ListOfFoodDonate.size());

		apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(dashboardResponseDto);

	}

}
