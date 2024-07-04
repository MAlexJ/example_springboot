package com.malex.simpleapplicationevent.listener;

import com.malex.simpleapplicationevent.event.SimpleApplicationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("simple")
@Slf4j
@Component
public class SimpleSpringEventListener implements ApplicationListener<SimpleApplicationEvent> {

  @Override
  public void onApplicationEvent(SimpleApplicationEvent event) {
    log.info("<<<< Handle simple event - {}, id -{}", event.getEvent(), event.getId());
  }
}
