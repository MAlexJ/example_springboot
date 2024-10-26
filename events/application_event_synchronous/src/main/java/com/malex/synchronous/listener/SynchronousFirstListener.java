package com.malex.synchronous.listener;

import com.malex.synchronous.base.AbstractSpringBootRunnerUtils;
import com.malex.synchronous.event.SynchronousEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Profile("synchronous")
@Slf4j
@Component
public class SynchronousFirstListener {

  @EventListener
  public void handleCustomSpringEvent(SynchronousEvent event) {
    log.warn(
        "<<<< Handle annotation by  - {},  event - {}, thread - {}",
        this.getClass().getSimpleName(),
        event.getMessage(),
        Thread.currentThread().getName());
    AbstractSpringBootRunnerUtils.sleep(1000);
  }
}
