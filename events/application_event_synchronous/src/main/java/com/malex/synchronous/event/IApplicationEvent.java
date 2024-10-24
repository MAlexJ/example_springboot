package com.malex.synchronous.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class IApplicationEvent extends ApplicationEvent {

  private final Object event;
  private final String id;

  public IApplicationEvent(Object source, Object event, String id) {
    super(source);
    this.event = event;
    this.id = id;
  }
}
