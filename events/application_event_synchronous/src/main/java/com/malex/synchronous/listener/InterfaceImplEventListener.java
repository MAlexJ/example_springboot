package com.malex.synchronous.listener;

import com.malex.synchronous.event.IApplicationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("simple")
@Slf4j
@Component
public class InterfaceImplEventListener implements ApplicationListener<IApplicationEvent> {

  @Override
  public void onApplicationEvent(IApplicationEvent event) {
    log.info(
        "<<<< Handle interface impl event - {}, id - {}, thread - {}",
        event.getEvent(),
        event.getId(),
        Thread.currentThread().getName());
  }
}
