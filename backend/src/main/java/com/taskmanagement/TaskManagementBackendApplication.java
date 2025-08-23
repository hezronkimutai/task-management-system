package com.taskmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TaskManagementBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementBackendApplication.class, args);
	}

}
