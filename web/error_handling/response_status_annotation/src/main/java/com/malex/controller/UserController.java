package com.malex.controller;

import com.malex.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> findUserById(@PathVariable Integer id) {

    if (id < 1) {
      throw new UserNotFoundException("User Not Found");
    }

    return ResponseEntity.ok(new UserDto(id));
  }

  public record UserDto(Integer id) {}
}
