package com.malex.limit_number_of_threads_to_call_external_service.semaphore_service;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SemaphoreService {

  /*
   * permits – the initial number of permits available.
   * This value may be negative, in which case releases must occur before
   * any acquires will be granted.
   */
  private final Semaphore semaphore = new Semaphore(10);

  public String parsePageWithSemaphoreRateLimiting() {
    boolean isAcquired;
    try {
      /*
       * Acquires a permit from this semaphore,
       * if one becomes available within the given waiting time
       * and the current thread has not been interrupted.
       */
      isAcquired = semaphore.tryAcquire(2, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      log.warn("Interrupted - {}", e.getMessage());
      /* SonarLint: "InterruptedException" and "ThreadDeath" should not be ignored
       *
       * If an InterruptedException or a ThreadDeath error is not handled properly,
       * the information that the thread was interrupted will be lost.
       * Handling this exception means either to re-throw it
       * or manually re-interrupt the current thread by calling Thread.interrupt().
       */
      Thread.currentThread().interrupt();
      throw new CustomAppException(e);
    }

    if (!isAcquired) {
      log.info("Не удалось захватить поток семафора");
      return "fallback result";
    }

    try {
      return "Success";
    } catch (Exception e) {
      return "fallback result";
    } finally {
      semaphore.release();
    }
  }

  public static class CustomAppException extends RuntimeException {
    public CustomAppException(Exception e) {}
  }
}
