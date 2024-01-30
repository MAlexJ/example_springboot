package com.example.controller;

import com.example.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1")
public class RestApiController {

  @PostMapping("/books")
  public ResponseEntity<String> validateUserRequestBody(@RequestBody UserDto userDto) {
    log.info("Request - {}", userDto);
    return ResponseEntity.ok("Ok: " + userDto.toString());
  }
}
