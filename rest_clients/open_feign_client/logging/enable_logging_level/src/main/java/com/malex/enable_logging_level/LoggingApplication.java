package com.malex.enable_logging_level;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableFeignClients
@SpringBootApplication
public class LoggingApplication {

  public static void main(String[] args) {
    SpringApplication.run(LoggingApplication.class, args);
  }

  @RestController
  @RequestMapping("/v1/api/todos")
  @RequiredArgsConstructor
  public static class RestApiController {

    private final OpenFeignWebclient feignClient;

    @GetMapping
    public ResponseEntity<List<Todo>> findAll() {
      return ResponseEntity.ok(feignClient.findAll());
    }
  }

  public record Todo(Long userId, Long id, String title, Boolean completed) {}
}
