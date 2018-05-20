package com.oess.webapp;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
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
