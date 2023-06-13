package com.fwm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fwm.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.fwm.dto.DashboardResponseDto;
import com.fwm.model.User;
import com.fwm.repository.UserRepository;
import com.fwm.service.IDashboardService;

@Service
public class DashboardServiceImpl implements IDashboardService {
	@Autowired
	private UserRepository userRepository;
	

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
		

		apiResponseDtoBuilder.withMessage("success").withStatus(HttpStatus.OK).withData(dashboardResponseDto);

	}

}
