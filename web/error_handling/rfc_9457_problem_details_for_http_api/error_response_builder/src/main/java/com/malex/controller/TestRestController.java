package com.malex.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

  @GetMapping("/test/{id}")
  public ResponseEntity<String> test(@PathVariable Long id) {

    if (id < 1) {
      throw new RuntimeException("Test, id must be greater than 0");
    }

    return ResponseEntity.ok("ok, id - %s".formatted(id));
  }

  @ExceptionHandler(RuntimeException.class)
  public ErrorResponse handle(RuntimeException ex, HttpServletRequest request) {
    return ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, ex.getMessage())
        .title("Business Error")
        .type(URI.create(request.getRequestURI()))
        .property("customCode", "BUSINESS_ERR")
        .build();
  }
}
