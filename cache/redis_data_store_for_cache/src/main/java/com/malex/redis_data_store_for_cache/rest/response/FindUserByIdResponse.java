package com.malex.redis_data_store_for_cache.rest.response;

import java.time.LocalDateTime;
import java.util.Objects;

public record FindUserByIdResponse(Long id, String username, LocalDateTime created) {

  public FindUserByIdResponse {
    Objects.requireNonNull(id, "id field cannot be null");
    Objects.requireNonNull(username, "username field cannot be null");
    Objects.requireNonNull(created, "created field cannot be null");
  }
}
