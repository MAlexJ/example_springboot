package com.malex.synchronous.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class IApplicationEvent extends ApplicationEvent {


  private final String id;

  public IApplicationEvent(Object source,  String id) {
    super(source);
    this.id = id;
  }
}
