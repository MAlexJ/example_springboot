package com.malexj.request_factory.controller;

import com.malexj.request_factory.webservice.RestClientService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class RestApiController {

  private static final Logger LOG = Logger.getLogger(RestApiController.class.getName());

  private final AtomicInteger counter = new AtomicInteger(0);

  private final RestClientService restClientService;

  public RestApiController(RestClientService restClientService) {
    this.restClientService = restClientService;
  }

  @GetMapping
  public ResponseEntity<String> info() {
    return ResponseEntity.ok(restClientService.getInfo());
  }

  @GetMapping("/internal")
  public ResponseEntity<String> internal() {
    if (counter.getAndIncrement() < 3) {
      return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
    LOG.info("info");
    return ResponseEntity.ok("info");
  }
}
