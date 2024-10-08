package com.malex.rate_limiting_external_service_resilience4j.controller;

import com.malex.rate_limiting_external_service_resilience4j.service.bulkhead_semaphore.BulkheadSemaphoreService;
import jakarta.websocket.server.PathParam;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiRestController {

  private final BulkheadSemaphoreService bulkheadSemaphoreService;

  @GetMapping("/v1/info/{value}")
  public ResponseEntity<String> info(
      @PathVariable String value, @PathParam(value = "delay") Boolean delay) {
    Objects.requireNonNull(value, "value must not be null");
    var delayVal = Optional.ofNullable(delay).filter(Boolean::booleanValue).orElse(false);
    return ResponseEntity.ok(bulkheadSemaphoreService.process(value, delayVal));
  }
}
