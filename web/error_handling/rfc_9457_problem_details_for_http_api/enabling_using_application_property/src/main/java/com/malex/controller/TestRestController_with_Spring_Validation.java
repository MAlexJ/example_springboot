package com.malex.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController_with_Spring_Validation {

  @PostMapping("/spring/validation")
  public ResponseEntity<String> calculate(@RequestBody @Valid OperationRequest operationRequest) {
    return ResponseEntity.ok(operationRequest.toString());
  }

  public record OperationRequest(
      @Min(value = 30, message = "base price greater than 30$ not allowed.") Double basePrice,
      Double discount) {}
}
