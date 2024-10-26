package com.malex.synchronous.publisher;

import com.malex.synchronous.base.AbstractSpringBootRunnerUtils;
import com.malex.synchronous.event.SynchronousEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("synchronous")
@Slf4j
@Component
@RequiredArgsConstructor
public class SynchronousEventPublisher {

  private final ApplicationEventPublisher eventPublisher;

  public void publishEvent(String message) {
    log.warn(">>>> Publishing synchronous event:");
    AbstractSpringBootRunnerUtils.sleep(1000);
    log.warn(
        ">>>> Publishing synchronous event: message - '{}', thread - '{}'",
        message,
        Thread.currentThread().getName());
    eventPublisher.publishEvent(new SynchronousEvent(this, message));
  }
}
