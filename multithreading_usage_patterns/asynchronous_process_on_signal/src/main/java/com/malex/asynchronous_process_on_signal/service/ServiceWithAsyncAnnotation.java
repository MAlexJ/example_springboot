package com.malex.asynchronous_process_on_signal.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServiceWithAsyncAnnotation {

  /*
   * Note:
   * To annotate method with @Async, we must definitely add the configuration @EnableAsync
   */
  @Async("runAsyncTasksElasticExecutor")
  public void runWithAnnotation(Runnable r) {
    log.info("Running with @Async annotation");
    r.run();
    log.info("End with @Async annotation");
  }
}
