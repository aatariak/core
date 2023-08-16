package com.namir.aatariak.sec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.namir.aatariak.sec")
public class AatariakSecApplication {

	public static void main(String[] args) {
		SpringApplication.run(AatariakSecApplication.class, args);
	}

}
