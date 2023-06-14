package com.fwm.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.fwm.service.IFoodService;

@Component
public class ScheduledTasks {
	@Autowired
	IFoodService foodService;
	
	//@Scheduled(cron = "0 * * ? * *")
	@EventListener(ApplicationReadyEvent.class)
	public void scheduleTaskToAddServerHeanth() {
		foodService.deliverFood();
		System.out.println("Task Run in a every minute");
	}
}
