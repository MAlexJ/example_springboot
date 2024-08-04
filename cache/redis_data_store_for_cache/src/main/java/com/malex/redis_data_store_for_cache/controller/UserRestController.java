package com.malex.redis_data_store_for_cache.controller;

import com.malex.redis_data_store_for_cache.model.User;
import com.malex.redis_data_store_for_cache.service.UserCacheService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserRestController {

  private final UserCacheService userService;

  @GetMapping
  public ResponseEntity<List<User>> findUsers() {
    return ResponseEntity.ok(userService.findAll());
  }

  /*
   * HTTP GET
   *
   * link: https://restfulapi.net/http-methods/
   *
   * 1. GET API Response Codes
   *  - For any given HTTP GET API, if the resource is found on the server,
   *      then it must return HTTP response code 200 (OK) – along with the response body,
   *      which is usually either XML or JSON content (due to their platform-independent nature).
   *
   *  - In case the resource is NOT found on the server then API must
   *    return HTTP response code 404 (NOT FOUND).
   *
   *  - Similarly, if it is determined that the GET request itself is not correctly
   *    formed then the server will return the HTTP response code 400 (BAD REQUEST).
   *
   */
  @GetMapping("/{id}")
  public ResponseEntity<User> findUserById(@PathVariable Integer id) {
    Objects.requireNonNull(id, "id must not be null");
    return userService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    Objects.requireNonNull(user, "user must not be null");
    return userService.save(user)
        ? ResponseEntity.ok(user)
        : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  /*
   * Difference between PUT and POST
   *
   * link: https://restfulapi.net/rest-put-vs-post/
   *
   * 1. HTTP specification clearly mentions that PUT method requests for the attached entity
   *    (in the request body)
   *
   * 2. PUT /questions/{question-id}
   *    If the Request-URI refers to an already existing resource – an update operation will happen,
   *    otherwise create operation should happen
   *
   * 3. PUT method is idempotent.
   *    So if we retry a request multiple times, that should be equivalent to a single request invocation.
   *
   *  4. Use PUT when we want to modify a singular resource that is already a part of resource collection.
   *
   *  5. Though PUT is idempotent, we should not cache its response
   *
   *  6. Generally, in practice, use PUT for UPDATE operations.
   */
  @PutMapping
  public ResponseEntity<User> updateUser(@RequestBody User user) {
    Objects.requireNonNull(user);
    return userService.update(user)
        ? ResponseEntity.ok(user)
        : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  /*
   * 4.7. Remove a device or configuration
   * link: https://restfulapi.net/rest-api-design-tutorial-with-example/
   *
   * 1. async operation
   *  A successful response SHOULD be 202 (Accepted) if the resource has been queued for deletion (async operation),
   *
   * 2. sync operation
   *  or 200 (OK) / 204 (No Content) if the resource has been deleted permanently (sync operation).
   *
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUserById(@PathVariable Integer id) {
    Objects.requireNonNull(id, "id must not be null");
    return userService.delete(id)
        ? ResponseEntity.status(HttpStatus.ACCEPTED).build()
        : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
}
