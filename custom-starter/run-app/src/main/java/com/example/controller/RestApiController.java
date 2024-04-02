package com.example.controller;

import com.malexj.copyrightstarter.service.CopyrightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/info")
@RequiredArgsConstructor
public class RestApiController {

  private final CopyrightService copyrightService;

  @GetMapping
  public ResponseEntity<String> info() {
    var response = copyrightService.generateCopyright();
    return ResponseEntity.ok(response);
  }
}
