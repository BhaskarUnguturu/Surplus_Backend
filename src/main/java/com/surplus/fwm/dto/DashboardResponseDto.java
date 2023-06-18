package com.surplus.fwm.dto;

public class DashboardResponseDto {
	private long totalCountOfUniversityMember;
	private long totalCountOfRestaurantMember;
	private long totalCountOfIndividualMember;
	private long totalCountOfNGO;
	private long totalCountOfRecipientIndividualMember;
	private long totalCountOfRecipientOrganizationMember;

	public long getTotalCountOfDonateFood() {
		return totalCountOfDonateFood;
	}

	public void setTotalCountOfDonateFood(long totalCountOfDonateFood) {
		this.totalCountOfDonateFood = totalCountOfDonateFood;
	}

	public long getTotalCountOfFoodPending() {
		return totalCountOfFoodPending;
	}

	public void setTotalCountOfFoodPending(long totalCountOfFoodPending) {
		this.totalCountOfFoodPending = totalCountOfFoodPending;
	}

	public long getTotalCountOfFoodInProgress() {
		return totalCountOfFoodInProgress;
	}

	public void setTotalCountOfFoodInProgress(long totalCountOfFoodInProgress) {
		this.totalCountOfFoodInProgress = totalCountOfFoodInProgress;
	}

	private long totalCountOfDonateFood;
	private long totalCountOfFoodPending;
	private long totalCountOfFoodInProgress;

	public long getTotalCountOfUniversityMember() {
		return totalCountOfUniversityMember;
	}

	public void setTotalCountOfUniversityMember(long totalCountOfUniversityMember) {
		this.totalCountOfUniversityMember = totalCountOfUniversityMember;
	}

	public long getTotalCountOfRestaurantMember() {
		return totalCountOfRestaurantMember;
	}

	public void setTotalCountOfRestaurantMember(long totalCountOfRestaurantMember) {
		this.totalCountOfRestaurantMember = totalCountOfRestaurantMember;
	}

	public long getTotalCountOfIndividualMember() {
		return totalCountOfIndividualMember;
	}

	public void setTotalCountOfIndividualMember(long totalCountOfIndividualMember) {
		this.totalCountOfIndividualMember = totalCountOfIndividualMember;
	}

	public long getTotalCountOfNGO() {
		return totalCountOfNGO;
	}

	public void setTotalCountOfNGO(long totalCountOfNGO) {
		this.totalCountOfNGO = totalCountOfNGO;
	}

	public long getTotalCountOfRecipientIndividualMember() {
		return totalCountOfRecipientIndividualMember;
	}

	public void setTotalCountOfRecipientIndividualMember(long totalCountOfRecipientIndividualMember) {
		this.totalCountOfRecipientIndividualMember = totalCountOfRecipientIndividualMember;
	}

	public long getTotalCountOfRecipientOrganizationMember() {
		return totalCountOfRecipientOrganizationMember;
	}

	public void setTotalCountOfRecipientOrganizationMember(long totalCountOfRecipientOrganizationMember) {
		this.totalCountOfRecipientOrganizationMember = totalCountOfRecipientOrganizationMember;
	}

}
