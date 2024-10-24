package com.malex.synchronous.event;

import java.time.Clock;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AnnotationApplicationEvent extends ApplicationEvent {

  private final String message;

  public AnnotationApplicationEvent(Object source, Clock clock, String message) {
    super(source, clock);
    this.message = message;
  }
}
