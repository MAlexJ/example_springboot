package com.malexj;

import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class RunApplication {

  @GetMapping("/api/info")
  public ResponseEntity<Info> info() {
    return ResponseEntity.ok(new Info("message: %s".formatted(UUID.randomUUID())));
  }

  public static void main(String[] args) {
    SpringApplication.run(RunApplication.class, args);
  }

  public record Info(String text) {}
}
