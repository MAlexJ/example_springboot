package com.malex;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@SpringBootApplication
public class MapstructMapperApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(MapstructMapperApplication.class, args);
  }

  @Override
  public void run(String... args) {
    log.info("Mapstruct mapper application started, args -{}", Arrays.toString(args));
  }

  @Component
  @RequiredArgsConstructor
  public static class ShutdownScheduler {

    private final ApplicationContext context;

    /*
     * Exit Spring application:
     * SpringApplication registers a shutdown hook with the JVM to make sure the application exits appropriately.
     *
     * link: https://www.baeldung.com/spring-boot-shutdown#exit
     */
    @Scheduled(fixedRate = 1000, initialDelay = 1000)
    public void shutdownApp() {
      log.info("Shutting down application");
      SpringApplication.exit(context);
    }
  }

  public static <T> T logDto(T t) {
    log.info("dto - {}", t);
    return t;
  }

  public static <T> T logEntity(T t) {
    log.info("entity - {}", t);
    return t;
  }
}
