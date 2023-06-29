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
		// food in inprogress
		dashboardResponseDto.setTotalCountOfFoodInProgress(foodRepository.countByStatus(0));
		// food in distribution
		dashboardResponseDto.setTotalCountOfFoodInDistribution(foodRepository.countByStatus(1));

		// food in collection
		dashboardResponseDto.setTotalCountOfFoodInCollection(foodRepository.countByStatus(2));
		// food in donat
		dashboardResponseDto.setTotalCountOfDonateFood(foodRepository.countByStatus(3));

		apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(dashboardResponseDto);

	}

}
