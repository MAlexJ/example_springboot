package com.malex;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

  @GetMapping("/test/{id}")
  public ResponseEntity<Void> test(@PathVariable Integer id) {

    if (id < 0) {
      throw new IllegalArgumentException("id must be greater than zero");
    }

    if (id < 10) {
      throw new RuntimeException("id must be less than 10");
    }

    return ResponseEntity.noContent().build();
  }
}
