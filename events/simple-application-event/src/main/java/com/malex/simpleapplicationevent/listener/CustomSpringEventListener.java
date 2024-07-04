package com.malex.simpleapplicationevent.listener;

import com.malex.simpleapplicationevent.event.CustomSpringEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Profile("custom")
@Slf4j
@Component
public class CustomSpringEventListener {

  @EventListener
  public void handleCustomSpringEvent(CustomSpringEvent event) {
    log.info("<<<< Handle custom event - {}", event.getMessage());
  }
}
