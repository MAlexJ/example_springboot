package com.malex.handler_exception_resolver_implementation.controller;

import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1")
public class RestApiController {

  private static final String URL = "https://dummyjson.com/products";

  private final RestTemplate restTemplate;

  public RestApiController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @GetMapping("/products/{productId}")
  public ResponseEntity<String> processRequest(@PathVariable Integer productId) {

    // NullPointerException
    Objects.requireNonNull(productId, "productId cannot be null");

    // 404 Not Found
    return restTemplate.getForEntity(URL + "/" + productId, String.class);
  }
}
