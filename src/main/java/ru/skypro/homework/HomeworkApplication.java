package ru.skypro.homework;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
@EnableAutoConfiguration
public class HomeworkApplication {
    public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication.class, args);
    }
}
