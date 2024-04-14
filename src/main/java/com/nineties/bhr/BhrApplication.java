package com.nineties.bhr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BhrApplication {

	public static void main(String[] args) {
		SpringApplication.run(BhrApplication.class, args);
	}

}
