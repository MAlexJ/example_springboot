package com.malex.restful.controller;

import com.malex.restful.model.User;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class GetRestApiController {

  private final List<User> repository = new CopyOnWriteArrayList<>();

  public record UserPage(List<User> users, int total, int size) {}

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
    UserPage page = new UserPage(repository, repository.size(), repository.size());
    return ResponseEntity.ok(page);
  }
}
