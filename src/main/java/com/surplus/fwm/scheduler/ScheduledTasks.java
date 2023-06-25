package com.surplus.fwm.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.surplus.fwm.service.IFoodService;

@Component
public class ScheduledTasks {
	@Autowired
	IFoodService foodService;
	
	//@Scheduled(cron = "0 0 */1 * * *")
	@EventListener(ApplicationReadyEvent.class)
	public void distributeFood() {
		foodService.distributeFood();
		System.out.println("Task Run in a every minute");
	}
	
	//@Scheduled(cron = "0 0 */1 * * *")
	@EventListener(ApplicationReadyEvent.class)
	public void completedFood() {
		foodService.completedFood();
		System.out.println("Task Run in a every minute");
	}
}
