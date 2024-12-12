package com.malex.restful.model;

import java.time.LocalDateTime;

public record User(Long id, String name, LocalDateTime created) {

  public User(Long id, User user) {
    this(id, user.name(), LocalDateTime.now());
  }
}
