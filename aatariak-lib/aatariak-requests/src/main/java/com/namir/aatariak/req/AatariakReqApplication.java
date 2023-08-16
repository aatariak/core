package com.namir.aatariak.req;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.namir.aatariak.req")
public class AatariakReqApplication {

    public static void main(String[] args) {
        SpringApplication.run(AatariakReqApplication.class, args);
    }
}
