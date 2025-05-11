package com.malex.parallel_execution_of_tasks_without_waiting.controller;

import com.malex.parallel_execution_of_tasks_without_waiting.service.SendInParallelService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiRestController {

  private final SendInParallelService service;

  @GetMapping("/v1/publish")
  public ResponseEntity<Void> publish(@RequestBody Message message) {
    service.sendMessageToSeveralTargets(message);
    return ResponseEntity.ok().build();
  }

  public record Message(String text) {
    public Message {
      Objects.requireNonNull(text, "text cannot be null");
    }
  }
}
