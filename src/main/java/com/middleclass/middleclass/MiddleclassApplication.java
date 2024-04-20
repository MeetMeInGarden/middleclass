package com.middleclass.middleclass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class MiddleclassApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiddleclassApplication.class, args);
	}
}