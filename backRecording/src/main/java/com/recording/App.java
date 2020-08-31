package com.recording;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.recording")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
