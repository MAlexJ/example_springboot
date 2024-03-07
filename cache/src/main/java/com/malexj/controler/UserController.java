package com.malexj.controler;

import com.malexj.model.User;
import com.malexj.service.UserService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping
  public ResponseEntity<List<User>> findUsers() {
    return ResponseEntity.ok(userService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> findUserById(@PathVariable Integer id) {
    return ResponseEntity.ok(userService.findById(id));
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    Objects.requireNonNull(user);
    return userService.save(user)
        ? ResponseEntity.ok(user)
        : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @PutMapping
  public ResponseEntity<User> updateUser(@RequestBody User user) {
    Objects.requireNonNull(user);
    return userService.update(user)
        ? ResponseEntity.ok(user)
        : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUserById(@PathVariable Integer id) {
    return userService.delete(id)
        ? ResponseEntity.ok("ser has been deleted by ID - " + id)
        : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
}
