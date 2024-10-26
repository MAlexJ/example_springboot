package com.malex.asynchronousevents.configuration;

import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class AsynchronousSpringEventsConfig {

  /*
   * Creating Asynchronous Events
   * In some cases, publishing events synchronously isn’t really what we’re looking for — we may need async handling of our events.
   *
   * link: https://www.baeldung.com/spring-events#anonymous-events
   */
  @Bean(name = "applicationEventMulticaster")
  public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
    var eventMulticaster = new SimpleApplicationEventMulticaster();
    eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
    return eventMulticaster;
  }

  @SneakyThrows
  public static void sleep(long millis) {
    TimeUnit.MILLISECONDS.sleep(millis);
  }
}
