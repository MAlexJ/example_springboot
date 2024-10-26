package com.malex.synchronous.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SynchronousEvent  extends ApplicationEvent {

    private final String message;

    public SynchronousEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}
