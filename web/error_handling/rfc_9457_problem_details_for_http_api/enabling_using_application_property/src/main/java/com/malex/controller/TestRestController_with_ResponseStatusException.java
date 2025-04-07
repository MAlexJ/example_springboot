package com.malex.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TestRestController_with_ResponseStatusException {

  @GetMapping("/test/{id}")
  public ResponseEntity<String> test(@PathVariable Integer id) {

    if (id < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
    }

    if (id < 10) {
      throw new RuntimeException("Invalid id");
    }

    return ResponseEntity.ok("Hello World");
  }
}
