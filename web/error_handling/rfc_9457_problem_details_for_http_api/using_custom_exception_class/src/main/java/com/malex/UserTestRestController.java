package com.malex;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserTestRestController {

  @GetMapping("/user/{id}")
  public ResponseEntity<UserDto> findById(@PathVariable Long id, HttpServletRequest request) {

    if (id < 1) {
      throw new UserNotFoundException(id, request.getContextPath());
    }

    return ResponseEntity.ok(new UserDto(1, "name"));
  }

  public record UserDto(int id, String name) {}
}
