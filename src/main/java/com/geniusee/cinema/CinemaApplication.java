package com.geniusee.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class CinemaApplication {
	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}
}
