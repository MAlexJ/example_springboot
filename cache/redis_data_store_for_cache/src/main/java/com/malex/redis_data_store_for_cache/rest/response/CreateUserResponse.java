package com.malex.redis_data_store_for_cache.rest.response;

import java.time.LocalDateTime;
import java.util.Objects;

public record CreateUserResponse(Long id, String username, LocalDateTime created) {

  public CreateUserResponse {
    Objects.requireNonNull(id, "id cannot be null");
    Objects.requireNonNull(username, "username cannot be null");
    Objects.requireNonNull(created, "created time cannot be null");
  }
}
