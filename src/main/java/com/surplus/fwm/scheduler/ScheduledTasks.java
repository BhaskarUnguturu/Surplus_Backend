package com.surplus.fwm.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

//import com.surplus.fwm.service.IFoodService;

@Component
public class ScheduledTasks {
//	@Autowired
//	IFoodService foodService;
	
	//@Scheduled(corn = "0 * * ? * *")
	@EventListener(ApplicationReadyEvent.class)
	public void scheduleTaskToAddServerHeanth() {
		//foodService.deliverFood();
		System.out.println("Task Run in a every minute");
	}
}
