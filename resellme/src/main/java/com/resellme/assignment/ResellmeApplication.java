package com.resellme.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ResellmeApplication {

	public static void main(String[] args) {
		System.out.println("Begin");
		SpringApplication.run(ResellmeApplication.class, args);
	}

}
