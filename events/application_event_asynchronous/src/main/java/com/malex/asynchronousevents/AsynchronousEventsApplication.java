package com.malex.asynchronousevents;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.malex.asynchronousevents.publisher.Publisher;

@EnableScheduling
@RequiredArgsConstructor
@SpringBootApplication
public class AsynchronousEventsApplication implements CommandLineRunner {

  private final Publisher publisher;

  public static void main(String[] args) {
    SpringApplication.run(AsynchronousEventsApplication.class, args);
  }

  @Override
  public void run(String... args) {
    publisher.publishEvent("Hello!");
  }
}
