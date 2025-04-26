package com.malex.listener.task.event;

import java.util.Objects;

public record TaskEventRequest(Long userId) {

  public TaskEventRequest {
    Objects.requireNonNull(userId, "User id must not be null");
  }
}
