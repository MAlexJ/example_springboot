package com.malex.simpleapplicationevent.publisher;

import com.malex.simpleapplicationevent.event.SimpleApplicationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("simple")
@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleSpringEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publishEvent(Object event, String id) {
    log.info(
        ">>>> Publishing simple event, object - '{}', thread - '{}'",
        event,
        Thread.currentThread().getName());
    applicationEventPublisher.publishEvent(new SimpleApplicationEvent(this, event, id));
  }
}
