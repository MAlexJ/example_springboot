package com.malexj.controller;


import java.util.List;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.malexj.model.UserRecord;
import com.malexj.service.AppService;

@RestController
@RequestMapping("/v1/users")
public class AppRestController {

  private final AppService service;

  public AppRestController(AppService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<UserRecord>> findAll() {
    return ResponseEntity.ok(service.findAllUsers());
  }

  @GetMapping("/{name}")
  public ResponseEntity<List<UserRecord>> findUsersByName(@PathVariable String name) {
    Objects.requireNonNull(name);
    return ResponseEntity.ok(service.findUsersByName(name));
  }
}
