package com.malexj.application_event_async_virtual_thread.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShutdownScheduler {

  private final ApplicationContext context;

  /*
   * Exit Spring application:
   * SpringApplication registers a shutdown hook with the JVM to make sure the application exits appropriately.
   *
   * link: https://www.baeldung.com/spring-boot-shutdown#exit
   */
  @Scheduled(fixedRate = 5000, initialDelay = 5000)
  public void shutdownApp() {
    log.warn("Shutting down application");
    SpringApplication.exit(context);
  }
}
