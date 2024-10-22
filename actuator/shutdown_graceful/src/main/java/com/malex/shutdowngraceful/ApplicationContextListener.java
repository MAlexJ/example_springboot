package com.malex.shutdowngraceful;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextListener implements ApplicationListener<ApplicationEvent> {

  private static final Logger LOG = LoggerFactory.getLogger(ApplicationContextListener.class);

  @Override
  public void onApplicationEvent(ApplicationEvent event) {

    if (event instanceof ContextClosedEvent) {
      LOG.info("Context closed");
    }

    if (event instanceof ContextStartedEvent) {
      LOG.info("Context started");
    }
  }
}
