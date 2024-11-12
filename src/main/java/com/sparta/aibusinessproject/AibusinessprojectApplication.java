package com.sparta.aibusinessproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.sparta.aibusinessproject")
@EnableJpaAuditing
public class AibusinessprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AibusinessprojectApplication.class, args);
    }

}
