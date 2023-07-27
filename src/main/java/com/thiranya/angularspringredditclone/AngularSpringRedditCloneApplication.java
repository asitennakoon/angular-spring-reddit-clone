package com.thiranya.angularspringredditclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AngularSpringRedditCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(AngularSpringRedditCloneApplication.class, args);
    }

}