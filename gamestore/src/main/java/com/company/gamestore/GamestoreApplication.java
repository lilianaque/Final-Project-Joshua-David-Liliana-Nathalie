package com.company.gamestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.company.gamestore"})
public class GamestoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamestoreApplication.class, args);
	}

}
