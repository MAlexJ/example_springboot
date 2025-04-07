package com.malex.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TestRestController {

  @GetMapping("/test/{id}")
  public ResponseEntity<Integer> test(@PathVariable Integer id) {
    if (id < 1) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
    }
    return ResponseEntity.ok(id);
  }
}
