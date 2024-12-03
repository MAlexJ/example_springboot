package com.malex.at.application.start;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunMethodOrCodeAtApplicationStartApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RunMethodOrCodeAtApplicationStartApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
