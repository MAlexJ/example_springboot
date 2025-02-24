package com.malexj;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

  @GetMapping("/api/info")
  public ResponseEntity<Info> info() {
    return ResponseEntity.ok(new Info("message: %s".formatted(UUID.randomUUID())));
  }

  public record Info(String text) {}
}
