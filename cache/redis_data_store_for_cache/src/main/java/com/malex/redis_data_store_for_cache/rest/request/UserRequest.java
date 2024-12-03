package com.malex.redis_data_store_for_cache.rest.request;

import java.time.LocalDateTime;

public record UserRequest(Long id, String username, LocalDateTime created) {}
