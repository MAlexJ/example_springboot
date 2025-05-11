package com.malex.aop_custom_annotation.controller;

import com.malex.aop_custom_annotation.aop.annotation.WithRateLimitProtection;
import com.malex.aop_custom_annotation.model.MyRequest;
import com.malex.aop_custom_annotation.model.MyResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class RestApiController {

  @PostMapping
  @WithRateLimitProtection
  public ResponseEntity<MyResponse> processRequest(@Valid @RequestBody MyRequest request) {
    longProcessing();
    return ResponseEntity.ok(new MyResponse(request.val(), UUID.randomUUID()));
  }

  @SneakyThrows
  private void longProcessing() {
    TimeUnit.SECONDS.sleep(10);
  }
}
