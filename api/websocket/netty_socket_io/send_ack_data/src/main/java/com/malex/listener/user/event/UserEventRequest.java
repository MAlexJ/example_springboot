package com.malex.listener.user.event;

import java.util.Objects;

public record UserEventRequest(Long userId) {

  public UserEventRequest {
    Objects.requireNonNull(userId, "User id must not be null");
  }
}
