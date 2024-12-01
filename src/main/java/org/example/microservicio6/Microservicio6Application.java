package org.example.microservicio6;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Microservicio6Application {

    private static final Logger logger = LoggerFactory.getLogger(Microservicio6Application.class);

    @Autowired
    private HealthMonitorController healthMonitorController;

    public static void main(String[] args) {
        SpringApplication.run(Microservicio6Application.class, args);
    }

    @PostConstruct
    public void init() {
        logger.info("Ejecución empezada");
        healthMonitorController.iniciarFlujo();
    }
}