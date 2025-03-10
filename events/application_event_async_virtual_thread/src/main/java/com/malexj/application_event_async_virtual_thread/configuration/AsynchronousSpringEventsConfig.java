package com.malexj.application_event_async_virtual_thread.configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

@Configuration
public class AsynchronousSpringEventsConfig {

  @Bean
  public ApplicationEventMulticaster applicationEventMulticaster() {
    var eventMulticaster = new SimpleApplicationEventMulticaster();
    eventMulticaster.setTaskExecutor(Executors.newVirtualThreadPerTaskExecutor());
    return eventMulticaster;
  }

  @SneakyThrows
  public static void sleep(long millis) {
    TimeUnit.MILLISECONDS.sleep(millis);
  }
}
