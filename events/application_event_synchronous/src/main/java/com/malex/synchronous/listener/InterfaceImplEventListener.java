package com.malex.synchronous.listener;

import com.malex.synchronous.event.IApplicationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("interface")
@Slf4j
@Component
public class InterfaceImplEventListener implements ApplicationListener<IApplicationEvent> {

  @Override
  public void onApplicationEvent(IApplicationEvent event) {
    log.warn(
        "<<<< Handle interface impl: id - {}, thread - {}",
        event.getId(),
        Thread.currentThread().getName());
  }
}
