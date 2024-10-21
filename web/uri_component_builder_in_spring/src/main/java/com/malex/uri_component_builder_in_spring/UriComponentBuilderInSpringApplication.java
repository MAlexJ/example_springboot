package com.malex.uri_component_builder_in_spring;

import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
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

  @RestController
  @RequestMapping("/api/v1")
  public static class REstApiController {

    @PostMapping
    public ResponseEntity<String> createCustomer(UriComponentsBuilder rootComponentsBuilder) {

      UriComponents uriComponents =
          rootComponentsBuilder
              .path("/api/v1/customers/{id}")
              .queryParam("id", UUID.randomUUID())
              .build();
      URI uri = uriComponents.toUri();
      /*
       * Create a new builder with a CREATED status and a location header set to the given URI.
       */
      return ResponseEntity.created(uri).build();
    }
  }
}
