package com.malex.restful.controller;

import com.malex.restful.model.User;
import com.malex.restful.repository.UserRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *
 */
@RestController
@RequestMapping("/api/v1") // URI Versioning
@RequiredArgsConstructor
public class PostRestApiController {

  private final UserRepository repository;

  @PostMapping("/users")
  public ResponseEntity<User> createUser200Ok(@RequestBody User user ) {

    // todo: Response status code for verification username field?
    Objects.requireNonNull(user.name(), "Username field cannot be null");

    return ResponseEntity.ok(repository.save(user));
  }
}
