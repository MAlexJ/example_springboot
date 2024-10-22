package com.malex.simpleapplicationevent.event;

import java.time.Clock;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CustomSpringEvent extends ApplicationEvent {

  private String message;

  public CustomSpringEvent(Object source, Clock clock, String message) {
    super(source, clock);
    this.message = message;
  }
}
