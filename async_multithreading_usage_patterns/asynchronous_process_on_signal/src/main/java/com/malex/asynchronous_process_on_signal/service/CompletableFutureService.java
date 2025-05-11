package com.malex.asynchronous_process_on_signal.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompletableFutureService {

  private final ExecutorService runAsyncTasksElasticExecutor;

  public void execute(Runnable r) {
    CompletableFuture.runAsync(
        () -> {
          log.info("Running CompletableFuture.runAsync");
          r.run();
          log.info("End CompletableFuture.runAsync");
        },
        runAsyncTasksElasticExecutor);
  }
}
