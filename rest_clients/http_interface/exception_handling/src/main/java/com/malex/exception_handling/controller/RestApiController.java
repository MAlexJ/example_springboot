package com.malex.exception_handling.controller;

import com.malex.exception_handling.webservice.WebHttpExchangeInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class RestApiController {

  private final WebHttpExchangeInterface webHttpExchangeInterface;

  @GetMapping("/status/{status}")
  public ResponseEntity<String> info(@PathVariable Integer status) {
    webHttpExchangeInterface.status(status);
    return ResponseEntity.ok("Hello World");
  }
}
