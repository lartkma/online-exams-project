package com.oess.webapp;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages="com.oess.model.entity")
@EnableJpaRepositories(basePackages="com.oess.model.repository")
public class WebApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(WebApplication.class, args);
        if ("true".equals(System.getProperty("shutdownWithEnter"))) {
            System.out.println("««Press Enter to terminate»»");
            new Scanner(System.in).nextLine();
            SpringApplication.exit(run);
        }
    }

}
