package com.taoche.alertmanage;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlertmanageApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AlertmanageApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("application started!");
    }
}
