package com.malex.asynchronous_process_on_signal.service;

import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectlyCallExecutorsService {

  private final ExecutorService runAsyncTasksElasticExecutor;

  public void execute(Runnable r) {
    runAsyncTasksElasticExecutor.execute(
        () -> {
          log.info("Running Asynchronous process, directly call");
          r.run();
          log.info("End Asynchronous process, directly call");
        });
  }
}
