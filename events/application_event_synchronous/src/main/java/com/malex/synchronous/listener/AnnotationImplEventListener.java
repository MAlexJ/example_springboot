package com.malex.synchronous.listener;

import com.malex.synchronous.event.AnnotationApplicationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Profile("annotation")
@Slf4j
@Component
public class AnnotationImplEventListener {

  @EventListener
  public void handleCustomSpringEvent(AnnotationApplicationEvent event) {
    log.warn(
        "<<<< Handle annotation impl event - {}, thread - {}",
        event.getMessage(),
        Thread.currentThread().getName());
  }
}
