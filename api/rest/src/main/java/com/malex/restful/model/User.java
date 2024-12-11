package com.malex.restful.model;

import java.time.LocalDateTime;

public record User(Long id, String username, LocalDateTime created) {

  public User(Long id, User user) {
    this(id, user.username(), LocalDateTime.now());
  }
}
