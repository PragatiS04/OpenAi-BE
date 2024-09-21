package com.challang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class ChhallangApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChhallangApplication.class, args);
	}
}