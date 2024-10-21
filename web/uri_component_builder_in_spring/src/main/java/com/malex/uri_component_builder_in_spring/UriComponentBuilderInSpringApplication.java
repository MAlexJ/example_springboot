package com.malex.uri_component_builder_in_spring;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
public class UriComponentBuilderInSpringApplication implements CommandLineRunner {

  private final UriBuilderExamples service;

  public static void main(String[] args) {
    SpringApplication.run(UriComponentBuilderInSpringApplication.class, args);
  }

  @Override
  public void run(String... args) {
    service.run();
  }
}
