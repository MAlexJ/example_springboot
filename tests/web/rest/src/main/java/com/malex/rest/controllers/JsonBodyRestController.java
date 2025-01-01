package com.malex.rest.controllers;

import com.malex.rest.service.WebService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/json")
public class JsonBodyRestController {

  private final WebService service;

  @GetMapping("/body")
  public ResponseEntity<JsonBody> getJsonBody() {
    var alex = new JsonBody(1L, "Alex", "");
    service.log(alex.toString());
    return ResponseEntity.ok(alex);
  }

  public record JsonBody(Long id, String name, String description) {}
}
