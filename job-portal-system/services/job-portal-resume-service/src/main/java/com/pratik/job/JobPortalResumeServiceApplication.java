package com.pratik.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JobPortalResumeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPortalResumeServiceApplication.class, args);
	}

}
