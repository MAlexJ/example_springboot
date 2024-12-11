package com.malex.restful.controller;

import com.malex.restful.model.User;
import com.malex.restful.model.UserPage;
import com.malex.restful.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GetRestApiController {

  private final UserRepository repository;

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
  @GetMapping("/users")
  public ResponseEntity<UserPage> findAll() {
    var users = repository.findAll();
    UserPage page = new UserPage(users, users.size());
    return ResponseEntity.ok(page);
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<User> findById(@PathVariable Long id) {

    // null verification
    Objects.requireNonNull(id, "id field must not be null");

    // verification of acceptable values
    if(id < 0){
      throw new IllegalArgumentException("id field must not be negative");
    }

    Optional<User> userOpt = repository.findById(id);
    User user = userOpt.orElseThrow(() -> new IllegalArgumentException("User not found"));
    return ResponseEntity.ok(user);
  }

}
