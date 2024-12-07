package com.sparta.aibusinessproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableCaching
@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = "com.sparta.aibusinessproject")
public class AibusinessprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AibusinessprojectApplication.class, args);
    }

}
