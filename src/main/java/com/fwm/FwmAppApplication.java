package com.fwm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fwm" })
@EnableScheduling
public class FwmAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FwmAppApplication.class, args);
	}

}
