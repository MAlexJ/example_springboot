package com.malex.redis_data_store_for_cache.rest.request;

import java.util.Objects;

public record CreateUserRequest(String username) {

  public CreateUserRequest {
    Objects.requireNonNull(username, "username cannot be null");
  }
}
