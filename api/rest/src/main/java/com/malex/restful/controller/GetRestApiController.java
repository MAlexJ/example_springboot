package com.malex.restful.controller;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.malex.restful.model.User;
import com.malex.restful.model.UserPage;
import com.malex.restful.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
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

  @GetMapping("/auth-endpoint/clients")
  public ResponseEntity<UserPage> authorizationFindAll(

      // base annotation for rest api
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, defaultValue = "") String auth) {

    // example: Authorization: Basic token_base64
    if (isNotAuthorized(auth)) {
      return ResponseEntity.status(UNAUTHORIZED).build();
    }

    // only for admin
    if (isNotAdmin(auth)) {
      return ResponseEntity.status(FORBIDDEN).build();
    }

    var users = repository.findAll();
    var page = new UserPage(users, users.size());
    return ResponseEntity.ok(page);
  }

  private boolean isNotAdmin(String auth) {
    return !auth.replace("Basic ", "").equalsIgnoreCase("admin");
  }

  boolean isNotAuthorized(String auth) {
    if (auth.isBlank()) {
      return true;
    }
    return !auth.startsWith("Basic");
  }
}
