package com.pingan.springbootfan01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringbootFan01Application {

    public static void main(String[] args) {
        //System.setProperty("java.awt.headless", "false");
        SpringApplication.run(SpringbootFan01Application.class, args);
    }

}
