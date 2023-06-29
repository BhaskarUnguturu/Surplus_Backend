package com.surplus.fwm.repository.custom.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.surplus.fwm.model.Food;
import com.surplus.fwm.repository.custom.FoodRepositoryCustom;

@Repository
public class FoodRepositoryCustomImpl implements FoodRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Food> getLast24HourDonatedFood() {
		Calendar cal = Calendar.getInstance();
		Date currentDate = new Date();
		cal.setTime(currentDate);
		cal.add(Calendar.HOUR, -24);
		Date oneHourBack = cal.getTime();
		String query = "SELECT t.* from food_details t where t.status = 3 and t.distribution_date between '"
				+ getFormatedDateFromDate(oneHourBack) + "' and '" + getFormatedDateFromDate(currentDate) + "'";
		Query queryString = entityManager.createNativeQuery(query, Food.class);
		List<Food> dataList = queryString.getResultList();
		return dataList;
	}

	public String getFormatedDateFromDate(Date date) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String strDate = formatter.format(date);
			return strDate;

		} catch (Exception e) {
		}
		return null;
	}

	public Date getDate(String date) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return formatter.parse(date);

		} catch (Exception e) {
		}
		return null;
	}

}
