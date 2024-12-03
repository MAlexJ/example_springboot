package com.malex.redis_data_store_for_cache.rest.response;

import java.util.List;

public record UsersResponse(List<UserResponse> users, Integer size) {

  public UsersResponse(List<UserResponse> users) {
    this(users, users.size());
  }
}
