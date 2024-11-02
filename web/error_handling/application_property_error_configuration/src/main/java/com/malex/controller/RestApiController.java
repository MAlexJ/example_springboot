package com.malex.controller;

import com.malex.service.ApiService;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class RestApiController {

  private final ApiService service;

  public RestApiController(ApiService service) {
    this.service = service;
  }

  @GetMapping("/status/{statusCode}")
  public ResponseEntity<Integer> errorStatus(@PathVariable String statusCode) {
    Objects.requireNonNull(statusCode, "statusCode must not be null");
    return ResponseEntity.ok(service.getStatus(statusCode));
  }
}
