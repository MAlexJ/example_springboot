package com.malexj.application_event_async_virtual_thread.listener;

import com.malexj.application_event_async_virtual_thread.configuration.AsynchronousSpringEventsConfig;
import com.malexj.application_event_async_virtual_thread.event.Event;
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
