package com.malex.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class RestApiController {

  /*
   * {@link DispatcherServlet} - DispatcherServlet class
   * {@link HandlerAdapter} - HandlerAdapter interface
   */
  @GetMapping("/hello")
  public ResponseEntity<String> hello() {
    return ResponseEntity.ok("Hello World");
  }
}
