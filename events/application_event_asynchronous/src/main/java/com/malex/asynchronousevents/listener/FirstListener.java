package com.malex.asynchronousevents.listener;

import com.malex.asynchronousevents.configuration.AsynchronousSpringEventsConfig;
import com.malex.asynchronousevents.event.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FirstListener {

  @EventListener
  public void handle(Event event) {
    log.warn(
        "<<<< Handle annotation by  - {},  event - {}, thread - {}",
        this.getClass().getSimpleName(),
        event,
        Thread.currentThread().getName());
    AsynchronousSpringEventsConfig.sleep(3000);
  }
}
