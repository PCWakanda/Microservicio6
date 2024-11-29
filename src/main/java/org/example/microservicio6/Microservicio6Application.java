package org.example.microservicio6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Microservicio6Application {

    public static void main(String[] args) {
        SpringApplication.run(Microservicio6Application.class, args);
    }
}