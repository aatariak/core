package com.namir.aatariak.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.namir.aatariak.user")
public class AatariakUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(AatariakUserApplication.class, args);
    }
}
