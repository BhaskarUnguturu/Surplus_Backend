package com.fwm.dto;

import java.util.List;

import com.fwm.model.Feedback;
import com.fwm.model.Food;
import com.fwm.model.Rating;

public class ReportResponseDto {
	private long userid;
	private String fullName;
	private String email;
	private String mobileNumber;
	private String type;
	private String address;
	private List<Rating> rating;
	private Food foods;
	private List<Feedback> feedback;

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}

	public Food getFoods() {
		return foods;
	}

	public void setFoods(Food foods) {
		this.foods = foods;
	}

	public List<Feedback> getFeedback() {
		return feedback;
	}

	public void setFeedback(List<Feedback> feedback) {
		this.feedback = feedback;
	}

}
