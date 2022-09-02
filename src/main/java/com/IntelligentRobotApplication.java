package com;

import love.forte.simbot.spring.autoconfigure.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableSimbot
@SpringBootApplication
public class IntelligentRobotApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntelligentRobotApplication.class, args);
    }

}
