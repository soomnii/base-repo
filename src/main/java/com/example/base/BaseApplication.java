package com.example.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class BaseApplication {
    public static void main(final String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }
}
