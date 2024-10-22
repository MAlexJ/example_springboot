package com.malex.shutdown;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class ShutdownListener implements ApplicationListener<ApplicationEvent> {

  private static final Logger LOG = LoggerFactory.getLogger(ShutdownListener.class);

  @Override
  public void onApplicationEvent(ApplicationEvent event) {

    if (event instanceof ContextClosedEvent) {
      // Handle shutdown event
      LOG.info("Application is shutting down...");
      // Perform cleanup tasks or any necessary operations before shutdown
    }

  }
}
