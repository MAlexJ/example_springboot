package com.malex.redis_data_store_for_cache.rest.response;

import java.util.List;

public record FindUsersResponse(List<FindUserByIdResponse> users, Integer size) {

  public FindUsersResponse(List<FindUserByIdResponse> users) {
    this(users, users.size());
  }
}
