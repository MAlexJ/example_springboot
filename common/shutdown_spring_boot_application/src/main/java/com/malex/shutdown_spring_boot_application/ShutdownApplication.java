package com.malex.shutdown_spring_boot_application;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShutdownApplication {

  @Bean
  public ShutdownListener listener() {
    return new ShutdownListener();
  }

  public static void main(String[] args) {
    SpringApplication.run(ShutdownApplication.class, args);
  }

  public static class ShutdownListener {

    private final Logger log = LoggerFactory.getLogger(ShutdownListener.class);

    @PreDestroy
    public void destroy() {
      log.info("Shutting down - {}", Thread.currentThread().getName());
    }
  }
}
