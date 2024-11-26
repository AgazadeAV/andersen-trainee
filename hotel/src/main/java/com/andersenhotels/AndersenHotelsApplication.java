package com.andersenhotels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.andersenhotels.model.entities")
@EnableJpaRepositories(basePackages = "com.andersenhotels.model.storage.jpa")
public class AndersenHotelsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AndersenHotelsApplication.class, args);
    }
}
