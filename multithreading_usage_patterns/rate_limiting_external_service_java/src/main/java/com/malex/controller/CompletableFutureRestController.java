package com.malex.controller;

import com.malex.service_with_completable_future.CompletableFutureService;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompletableFutureRestController {

  private final CompletableFutureService completableFutureService;

  @GetMapping(path = "/v1/rate-limit/completable-future/{value}")
  public CompletableFuture<String> rateLimit(@PathVariable String value) {

    if (!StringUtils.hasText(value)) {
      return CompletableFuture.failedFuture(new NoSuchElementException("Value cannot be empty"));
    }

    return completableFutureService.rateLimiting(value);
  }
}
