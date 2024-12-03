package com.malex.redis_data_store_for_cache.rest.response;

import java.time.LocalDateTime;

public record UserResponse(Long id, String username, LocalDateTime created) {}
