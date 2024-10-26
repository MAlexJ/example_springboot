package com.malex.synchronous.publisher;

import com.malex.synchronous.event.IApplicationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("interface")
@Slf4j
@Component
@RequiredArgsConstructor
public class InterfaceImplEventPublisher {

  private final ApplicationEventPublisher eventPublisher;

  public void publishEvent(String id) {
    log.warn(
        ">>>> Publishing interface impl event, object - '{}', thread - '{}'",
        id,
        Thread.currentThread().getName());
    eventPublisher.publishEvent(new IApplicationEvent(this, id));
  }
}
