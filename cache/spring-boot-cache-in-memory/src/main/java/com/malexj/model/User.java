package com.malexj.model;

import java.time.LocalDateTime;
import java.util.Objects;

public record User(Integer id, String username, LocalDateTime created) {

  public User(Integer id, String username, LocalDateTime created) {
    this.id = id;
    this.username = username;
    this.created = Objects.isNull(created) ? LocalDateTime.now() : created;
  }
}
