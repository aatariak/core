package com.namir.aatariak.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.namir.aatariak.storage")
public class AatariakStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(AatariakStorageApplication.class, args);
    }
}
