package com.malex.service.completable_future_service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/*
 * With CompletableFuture you can manage the result more flexibly, waiting for it when needed,
 * setting up a timeout and handling errors
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CompletableFutureService {

  private final ExecutorService runAsyncTasksElasticExecutor;

  public CompletableFuture<String> rateLimiting(String value) {
    try {

      /*
       * 1. We pass the call of external service to the allocated stream pool,
       *    with the second parameter in the CompletableFuture.supplyAsync(..., parserElasticExecutor) method
       *    explicitly specifying the pool
       */
      return CompletableFuture.supplyAsync(() -> process(value), runAsyncTasksElasticExecutor)

          /*
           * 2. Set 4 seconds timer to wait for a response
           */
          .orTimeout(10, TimeUnit.SECONDS)

          /*
           * 3. In the operator . exceptionally(...) we catch possible exceptions when calling a service
           *    and return fallback
           */
          .exceptionally(
              e -> {
                log.warn("Error when calling external service", e);
                throw new RejectedExecutionException();
              });

      /*
       * 4. Try-catch all the code, because if the runAsyncTasksElasticExecutor queue is full,
       *    then when trying to call supplyAsync(..) it will throw RejectedExecutionException exception,
       *    in which case we also return fallback
       */
    } catch (RejectedExecutionException e) {
      log.warn("Overcrowded queue for tasks to be performed by external service");
      return CompletableFuture.completedFuture("fallback result");
    }
  }

  private String process(String value) {
    var response = String.format("Success - %s", value);
    log.info("CompletableFuture, response from external service: {}", response);
    return response;
  }
}
