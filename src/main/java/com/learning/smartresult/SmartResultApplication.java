package com.learning.smartresult;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.learning.smartresult")
public class SmartResultApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartResultApplication.class, args);
	}

}
