package com.example.webshopapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebshopApiApplication {

    private WebshopApiApplication() {
        // Prevent instantiation
    }

    public static void main(String[] args) {
        SpringApplication.run(WebshopApiApplication.class, args);
    }

}