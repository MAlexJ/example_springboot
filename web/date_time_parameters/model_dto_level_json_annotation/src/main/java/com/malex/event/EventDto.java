package com.malex.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record EventDto(
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = UTC) LocalDateTime startDate,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = UTC) LocalDateTime endDate,
    LocalDateTime date) {

  public static final String UTC = "UTC";
}
