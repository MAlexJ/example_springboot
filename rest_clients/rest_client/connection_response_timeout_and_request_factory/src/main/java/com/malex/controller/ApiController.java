package com.malex.controller;

import com.malex.webservice.WebService;
import java.util.concurrent.TimeUnit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class ApiController {

  private final WebService webService;

  public ApiController(WebService webService) {
    this.webService = webService;
  }

  @GetMapping("/internal")
  public ResponseEntity<String> internal() throws InterruptedException {
    TimeUnit.SECONDS.sleep(3L);
    return ResponseEntity.ok("This is the internal API");
  }

  @GetMapping("/info")
  public ResponseEntity<String> info() {
    return ResponseEntity.ok(webService.findInfo());
  }
}
