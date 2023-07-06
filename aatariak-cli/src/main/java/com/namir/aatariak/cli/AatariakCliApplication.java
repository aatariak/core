package com.namir.aatariak.cli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.namir.aatariak")
public class AatariakCliApplication {
    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(AatariakCliApplication.class, args)));
    }
}
