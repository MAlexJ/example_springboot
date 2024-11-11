package com.malexj.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignatureRestController {

  @GetMapping("/v1/signature")
  public ResponseEntity<String> signature() {
    return ResponseEntity.ok("Best regards!");
  }
}
