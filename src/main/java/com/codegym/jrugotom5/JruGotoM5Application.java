package com.codegym.jrugotom5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JruGotoM5Application {
    public static void main(String[] args) {
        SpringApplication.run(JruGotoM5Application.class, args);
    }
}

