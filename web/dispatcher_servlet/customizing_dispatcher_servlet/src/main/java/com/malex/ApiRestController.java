package com.malex;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class ApiRestController {

  @GetMapping("/{id}")
  public ResponseEntity<User> getUser(@PathVariable String id) {
    return ResponseEntity.ok(new User(id, UUID.randomUUID().toString()));
  }

  public record User(String id, String name) {}
}
