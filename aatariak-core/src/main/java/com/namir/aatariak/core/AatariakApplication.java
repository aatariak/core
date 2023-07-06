package com.namir.aatariak.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.namir.aatariak.core")
public class AatariakApplication {

	public static void main(String[] args) {
		SpringApplication.run(AatariakApplication.class, args);
	}

}
