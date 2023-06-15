package com.surplus.fwm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.surplus.fwm.model.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

	List<Food> findByUserId(long userId);

	List<Food> findByStatus(int status);

}
