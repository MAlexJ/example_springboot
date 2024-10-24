package com.malex.synchronous.publisher;

import com.malex.synchronous.event.AnnotationApplicationEvent;
import java.time.Clock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("annotation")
@Slf4j
@Component
@RequiredArgsConstructor
public class AnnotationImplEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publishEvent(final String message) {
    log.info(
        ">>>> Publishing annotation iml event, message - '{}', thread - '{}'",
        message,
        Thread.currentThread().getName());
    var customSpringEvent = new AnnotationApplicationEvent(this, Clock.systemUTC(), message);
    applicationEventPublisher.publishEvent(customSpringEvent);
  }
}
