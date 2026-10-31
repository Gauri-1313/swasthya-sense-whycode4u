package com.swasthya.sense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SwasthyaSenseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwasthyaSenseApplication.class, args);
        System.out.println("==================================================================");
        System.out.println("  Swasthya Sense (स्वास्थ्य सेंस) Backend Initialized Successfully!");
        System.out.println("  API Endpoint: http://localhost:8080/api/patients/alerts");
        System.out.println("==================================================================");
    }
}

