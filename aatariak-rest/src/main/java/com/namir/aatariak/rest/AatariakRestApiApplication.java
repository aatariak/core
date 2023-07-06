package com.namir.aatariak.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.namir.aatariak")
public class AatariakRestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AatariakRestApiApplication.class, args);
    }
}
