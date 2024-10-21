package com.malex.uri_component_builder_in_spring.shutdown.scheduler;

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

  @Scheduled(fixedRate = 10000, initialDelay = 10000)
  public void shutdownApp() {
    log.info("Shutting down application");
    SpringApplication.exit(context);
  }
}
