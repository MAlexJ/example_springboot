package com.malex.simpleapplicationevent.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SimpleApplicationEvent extends ApplicationEvent {

  private final Object event;
  private final String id;

  public SimpleApplicationEvent(Object source, Object event, String id) {
    super(source);
    this.event = event;
    this.id = id;
  }
}
