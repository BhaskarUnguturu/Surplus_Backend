package com.surplus.fwm.dto;

import java.sql.Date;

public class FoodDto {
	private String typeOfFood;
	private int quantity;
	private Date expirationDate;
	private String dietaryRestrictions;
	private Date schedulingTimeDate;
	private String venue;
	private String typeOfDonation;
	private long userId;
	private Long receipentId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Long getReceipentId() {
		return receipentId;
	}

	public void setReceipentId(Long receipentId) {
		this.receipentId = receipentId;
	}

	public String getTypeOfFood() {
		return typeOfFood;
	}

	public void setTypeOfFood(String typeOfFood) {
		this.typeOfFood = typeOfFood;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getDietaryRestrictions() {
		return dietaryRestrictions;
	}

	public void setDietaryRestrictions(String dietaryRestrictions) {
		this.dietaryRestrictions = dietaryRestrictions;
	}

	public Date getSchedulingTimeDate() {
		return schedulingTimeDate;
	}

	public void setSchedulingTimeDate(Date schedulingTimeDate) {
		this.schedulingTimeDate = schedulingTimeDate;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getTypeOfDonation() {
		return typeOfDonation;
	}

	public void setTypeOfDonation(String typeOfDonation) {
		this.typeOfDonation = typeOfDonation;
	}

}
