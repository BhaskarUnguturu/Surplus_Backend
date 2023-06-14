package com.fwm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fwm.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

	List<Feedback> findByUserId(long userId);

}
