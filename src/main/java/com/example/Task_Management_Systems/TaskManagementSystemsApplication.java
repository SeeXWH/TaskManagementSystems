package com.example.Task_Management_Systems;

import com.example.Task_Management_Systems.service.TaskService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@OpenAPIDefinition
public class TaskManagementSystemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementSystemsApplication.class, args);
	}

}
