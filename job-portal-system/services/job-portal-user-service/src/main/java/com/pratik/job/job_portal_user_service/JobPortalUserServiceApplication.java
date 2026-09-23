package com.pratik.job.job_portal_user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JobPortalUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPortalUserServiceApplication.class, args);
	}

}
