package com.malex.redis_data_store_for_cache.rest.request;

import java.time.LocalDateTime;
import java.util.Objects;

public record UpdateUserRequest(Long id, String username, LocalDateTime created) {

  public UpdateUserRequest {
    Objects.requireNonNull(id, "id field cannot be null");
    Objects.requireNonNull(username, "username field cannot be null");
    Objects.requireNonNull(created, "created field cannot be null");
  }
}
