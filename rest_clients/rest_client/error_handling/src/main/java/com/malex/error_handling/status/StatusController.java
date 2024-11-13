package com.malex.error_handling.status;

import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
public class StatusController {

  private final StatusWebClient webService;

  public StatusController(StatusWebClient webService) {
    this.webService = webService;
  }

  @GetMapping("/{status}")
  public ResponseEntity<String> status(@PathVariable Integer status) {
    Objects.requireNonNull(status, "Status must not be null");
    return ResponseEntity.ok(webService.getStatus(status));
  }
}
