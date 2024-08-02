package com.JobAppBackend.JobFreakBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JobFreakBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobFreakBackendApplication.class, args);
	}

}
