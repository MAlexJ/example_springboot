package com.malex.rest.controllers;

import com.malex.rest.service.WebService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/status")
public class StatusRestController {

  private final WebService service;

  @GetMapping("/200/ok")
  public ResponseEntity<Void> statusOk() {
    service.log("ok");
    return ResponseEntity.ok().build();
  }

  @GetMapping("/404/notFound")
  public ResponseEntity<Void> status404NotFound() {
    service.log("notFound");
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/400/badRequest")
  public ResponseEntity<Void> status400BadRequest() {
    service.log("badRequest");
    return ResponseEntity.badRequest().build();
  }
}
