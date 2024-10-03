package com.malex.limit_number_of_threads_to_call_external_service.completable_future_service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompletableFutureService {

  private final ExecutorService runAsyncTasksElasticExecutor;

  public CompletableFuture<String> parsePageWithRateLimitingCompletableFuture() {
    try {
      return CompletableFuture.supplyAsync(this::parsePage, runAsyncTasksElasticExecutor)
          .orTimeout(4, TimeUnit.SECONDS)
          .exceptionally(
              e -> {
                log.warn("Ошибка при вызове внешнего сервиса", e);
                return "fallback result";
              });
    } catch (RejectedExecutionException e) {
      log.warn("Переполнена очередь выполонения задач на вызов внешнего сервиса");
      return CompletableFuture.completedFuture("fallback result");
    }
  }

  private String parsePage() {
    var response = "Success";
    log.info(response);
    return response;
  }
}
