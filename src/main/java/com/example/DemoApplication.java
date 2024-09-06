package com.example;


import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableRetry
@SpringBootApplication
@EnableConfigurationProperties
public class DemoApplication {
    public static void main(String[] args) {
        try {
            long begin = System.currentTimeMillis();
            SpringApplication.run(DemoApplication.class, args);
            long end = System.currentTimeMillis();
        } catch (Exception e) {
            System.out.print( e);
        }
    }
}