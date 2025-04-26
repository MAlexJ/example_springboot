package com.malex.controller;

import com.malex.model.User;
import com.malex.model.UserPage;
import com.malex.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
* HTTP GET:
*
* link: https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/GET
*
  The GET HTTP method requests a representation of the specified resource.
  Requests using GET should only be used to request data and shouldn't contain a body.
*
* Method Definitions:
*
  Request has body - No
  Successful response has body - Yes
  Safe - Yes
  Idempotent - Yes
  Cacheable - Yes
*/
@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GetRestApiController {

  private final UserRepository repository;

  @GetMapping("/users")
  public ResponseEntity<UserPage> findAll(@RequestParam(defaultValue = "") String name) {
    List<User> users;
    if (name.isBlank()) {
      // default
      users = repository.findAll();
    } else {
      // apply filtering
      users = repository.findAll(name);
    }
    var page = new UserPage(users, users.size());
    return ResponseEntity.ok(page);
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<User> findById(@PathVariable Long id) {

    // null verification
    Objects.requireNonNull(id, "id field must not be null");

    // verification of acceptable values
    if (id < 0) {
      log.warn("id field must not be negative");
      return ResponseEntity.badRequest().build();
    }

    return repository
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
