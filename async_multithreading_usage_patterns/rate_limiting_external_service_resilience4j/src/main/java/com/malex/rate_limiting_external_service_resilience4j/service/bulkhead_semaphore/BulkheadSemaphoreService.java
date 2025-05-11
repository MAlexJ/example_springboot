package com.malex.rate_limiting_external_service_resilience4j.service.bulkhead_semaphore;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BulkheadSemaphoreService {

  /*
   * yaml configuration:
   *
   * exampleSemaphore - configuration name
   * maxConcurrentCalls - number of semaphore permits
   * maxWaitDuration - the time during which the stream will be waiting for the semaphore to resolve,
   *                   if the timeout is exceeded, the exception will be thrown out BulkheadFullException
   */
  @Bulkhead(name = "exampleSemaphore", fallbackMethod = "fallback")
  public String process(String request, boolean delay) {
    return handleRequest(request, delay);
  }

  @SneakyThrows
  private String handleRequest(String request, boolean delay) {
    log.info("Processing request: {}", request);
    if (delay) {
      Thread.sleep(100000);
    }
    return request.toUpperCase();
  }

  public String fallback(String request, Exception e) {
    log.warn("fallback method occurred , request - {}, ex message - {}", request, e.getMessage());
    return "fallback result";
  }
}
