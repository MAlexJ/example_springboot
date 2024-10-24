package com.malex.synchronous.publisher;

import com.malex.synchronous.event.IApplicationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("simple")
@Slf4j
@Component
@RequiredArgsConstructor
public class InterfaceImplEventPublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  public void publishEvent(Object event, String id) {
    log.info(
        ">>>> Publishing interface impl event, object - '{}', thread - '{}'",
        event,
        Thread.currentThread().getName());
    applicationEventPublisher.publishEvent(new IApplicationEvent(this, event, id));
  }
}
