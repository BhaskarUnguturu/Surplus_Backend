package com.surplus.fwm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.surplus.fwm.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

	List<Rating> findByUserId(long userId);

}
