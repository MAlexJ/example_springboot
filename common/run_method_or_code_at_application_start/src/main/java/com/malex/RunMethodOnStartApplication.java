package com.malex;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class RunMethodOnStartApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(RunMethodOnStartApplication.class, args);
  }

  @Override
  public void run(String... args) {
    log.info("1. implements CommandLineRunner interface");
  }
}
