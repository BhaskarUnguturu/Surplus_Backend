package com.surplus.fwm.dto;

import java.util.Date;

public class FoodResponseDto {
	private Long id;
	private Date createdAt;
	private Date updatedAt;
	private long userId;
	private Date schedulingTimeDate;
	private String venue;
	private String typeOfDonation;
	private String typeOfFood;
	private int quantity;
	private Date expirationDate;
	private String dietaryRestrictions;
	private Long receipentId;
	private String receipentName;
	private Integer status;
	private int rating;
	private String feedback;

	public String getReceipentName() {
		return receipentName;
	}

	public void setReceipentName(String receipentName) {
		this.receipentName = receipentName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public Long getReceipentId() {
		return receipentId;
	}

	public void setReceipentId(Long receipentId) {
		this.receipentId = receipentId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

}
