package com.malex.asynchronous_process_on_signal.controller;

import com.malex.asynchronous_process_on_signal.service.CompletableFutureService;
import com.malex.asynchronous_process_on_signal.service.DirectlyCallExecutorsService;
import com.malex.asynchronous_process_on_signal.service.ServiceWithAsyncAnnotation;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppRestController {

  private final CompletableFutureService completableFutureService;
  private final ServiceWithAsyncAnnotation asyncWithAnnotationService;
  private final DirectlyCallExecutorsService directlyCallExecutorsService;

  @GetMapping("/v1/tasks/{name}")
  public ResponseEntity<Void> runAsynchronousProcess(@PathVariable String name) {
    return runTask(name);
  }

  private ResponseEntity<Void> runTask(String name) {
    return switch (name.toLowerCase()) {
      case "call_directly" -> {
        /*
         * run on [pool-1-thread-1] thread
         */
        directlyCallExecutorsService.execute(this::executeLongOperation);
        yield ResponseEntity.ok().build();
      }
      case "async" -> {
        /*
         * run on [pool-1-thread-1] thread
         */
        asyncWithAnnotationService.runWithAnnotation(this::executeLongOperation);
        yield ResponseEntity.ok().build();
      }
      case "completable_future" -> {
        /*
         * run on [pool-1-thread-1] thread
         */
        completableFutureService.execute(this::executeLongOperation);
        yield ResponseEntity.ok().build();
      }
      default -> throw new UnsupportedOperationException("Unsupported task name: " + name);
    };
  }

  @SneakyThrows
  private void executeLongOperation() {
    TimeUnit.SECONDS.sleep(10);
  }
}
