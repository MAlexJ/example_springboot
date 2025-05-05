package example.java.com.malex.service;

import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AsyncService {

  @Async("virtualThreadExecutor")
  public void runAsyncTaskVoid() {
    log.info(">>> Start runAsyncTaskVoid");
    hardIoTask();
    log.info(">>> End runAsyncTaskVoid");
  }

  @Async("virtualThreadExecutor")
  public CompletableFuture<String> runAsyncTaskCompletableFuture() {
    log.info(">>> Start runAsyncTaskCompletableFuture");
    hardIoTask();
    log.info(">>> End runAsyncTaskCompletableFuture");
    return CompletableFuture.completedFuture("Result");
  }

  private void hardIoTask() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      log.error("Error in hardIOPart", e);
      Thread.currentThread().interrupt();
    }
  }
}
