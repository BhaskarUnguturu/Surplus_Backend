package com.surplus.fwm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.surplus.fwm.property.FileStorageProperties;

@SpringBootApplication
@ComponentScan(basePackages = { "com.surplus.fwm" })
@EnableConfigurationProperties({ FileStorageProperties.class })
@EnableScheduling
public class FwmAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FwmAppApplication.class, args);
	}

}
