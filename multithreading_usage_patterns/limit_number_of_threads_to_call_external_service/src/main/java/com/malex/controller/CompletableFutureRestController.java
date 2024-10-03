package com.malex.controller;

import com.malex.service.completable_future_service.CompletableFutureService;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompletableFutureRestController {

  private final CompletableFutureService completableFutureService;

  @RequestMapping(path = "/v1/rate-limit/completable-future/{value}", method = RequestMethod.GET)
  public @ResponseBody CompletableFuture<String> rateLimit(@PathVariable String value) {
    if (!StringUtils.hasText(value)) {
      return CompletableFuture.failedFuture(new IllegalArgumentException("Value cannot be empty"));
    }
    return completableFutureService.rateLimiting(value);
  }
}
