package com.malex.asynchronousevents.publisher;

import com.malex.asynchronousevents.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

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
