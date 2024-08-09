package com.malex.request_validation.controller;

import com.malex.request_validation.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class RestApiController {

  /*
   * @Valid - Constraints defined on the object and its properties are validated when the property, method parameter
   *          or method return type is validated.
   */
  @PostMapping("/users")
  public ResponseEntity<User> validateUserRequestBody(@RequestBody @Valid User user) {
    return ResponseEntity.ok(user);
  }
}
