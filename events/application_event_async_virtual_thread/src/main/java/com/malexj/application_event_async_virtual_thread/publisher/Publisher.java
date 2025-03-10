package com.malexj.application_event_async_virtual_thread.publisher;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.malexj.application_event_async_virtual_thread.event.Event;

@Slf4j
@Component
@RequiredArgsConstructor
public class Publisher {

  private final ApplicationEventPublisher eventPublisher;

  public void publishEvent(String message) {
    log.warn(
        ">>>> Publishing synchronous event: message - '{}', thread - '{}'",
        message,
        Thread.currentThread().getName());
    eventPublisher.publishEvent(new Event(message));
  }
}
