package com.surplus.fwm.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.surplus.fwm.service.IFoodService;

@Component
public class ScheduledTasks {
	@Autowired
	IFoodService foodService;

	@Scheduled(cron = "0 0/5 * * * ?")
	// @EventListener(ApplicationReadyEvent.class)
	public void distributeFood() {
		foodService.distributeFood();
		System.out.println("Scheduler run in a every 5 minute for distribute food");
	}

	@Scheduled(cron = "0 0/10 * * * ?")
	// @EventListener(ApplicationReadyEvent.class)
	public void completedFood() {
		foodService.completedFood();
		System.out.println("Scheduler run in a every 10 minute for food donation");
	}
}
