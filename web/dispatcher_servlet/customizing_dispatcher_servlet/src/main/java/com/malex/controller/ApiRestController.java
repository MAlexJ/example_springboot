package com.malex.controller;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class ApiRestController {

  private static final Logger LOGGER = Logger.getLogger(ApiRestController.class.getName());

  @GetMapping("/{id}")
  public ResponseEntity<User> getUser(@PathVariable String id) {
    var threadName = Thread.currentThread().getName();

    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, threadName, e);
    }
    LOGGER.info(() -> ">>> Thread - " + threadName);
    return ResponseEntity.ok(new User(id, threadName));
  }

  public record User(String id, String threadName) {}
}
