package com.ra.md05_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Md05ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(Md05ProjectApplication.class, args);
    }

}
