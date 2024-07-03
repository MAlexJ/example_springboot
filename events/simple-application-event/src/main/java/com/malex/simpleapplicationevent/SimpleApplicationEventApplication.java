package com.malex.simpleapplicationevent;

import com.malex.simpleapplicationevent.publisher.CustomSpringEventPublisher;
import com.malex.simpleapplicationevent.publisher.SimpleSpringEventPublisher;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class SimpleApplicationEventApplication implements CommandLineRunner {

  private final CustomSpringEventPublisher customSpringEventPublisher;
  private final SimpleSpringEventPublisher simpleSpringEventPublisher;

  /** Spring allows us to create and publish custom events that by default are synchronous */
  public static void main(String[] args) {
    log.info(
        "Spring allows us to create and publish custom events that by default are synchronous");
    SpringApplication.run(SimpleApplicationEventApplication.class, args);
  }

  @Override
  public void run(String... args) {
    /* 1. publish simple event */
    simpleSpringEventPublisher.publishEvent("{json}", UUID.randomUUID().toString());

    /* 2. publish custom event */
    customSpringEventPublisher.publishEvent("Custom hello!");
  }
}
