package com.surplus.fwm.repository.custom;

import java.util.List;

import com.surplus.fwm.model.Food;

public interface FoodRepositoryCustom {
	List<Food> getLast24HourDonatedFood();
}
