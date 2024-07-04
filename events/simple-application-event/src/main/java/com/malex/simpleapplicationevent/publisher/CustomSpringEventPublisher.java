package com.malex.simpleapplicationevent.publisher;

import com.malex.simpleapplicationevent.event.CustomSpringEvent;
import java.time.Clock;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("custom")
@Slf4j
@Component
public class CustomSpringEventPublisher implements ApplicationEventPublisherAware {

  private ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void setApplicationEventPublisher(
      @NonNull ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void publishEvent(final String message) {
    log.info(
        ">>>> Publishing custom event, message - '{}', thread - '{}'",
        message,
        Thread.currentThread().getName());
    var customSpringEvent = new CustomSpringEvent(this, Clock.systemUTC(), message);
    applicationEventPublisher.publishEvent(customSpringEvent);
  }
}
