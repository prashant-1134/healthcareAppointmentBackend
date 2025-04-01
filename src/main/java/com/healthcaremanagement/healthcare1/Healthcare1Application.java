package com.healthcaremanagement.healthcare1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Healthcare1Application {

	public static void main(String[] args) {
		SpringApplication.run(Healthcare1Application.class, args);
	}

}
