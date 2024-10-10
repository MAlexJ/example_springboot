package com.malex.leaky_bucket_with_bucket4j_and_redis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malex.leaky_bucket_with_bucket4j_and_redis.service.RestService;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class RestApiController {

    private final RestService service;

    @GetMapping("/{value}")
    public ResponseEntity<String> processRequest(@PathVariable String value){
      return ResponseEntity.ok(service.process(value));
    }
}

