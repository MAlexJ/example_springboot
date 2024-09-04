package com.malex.request_level_configuration.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EventStartEndDatePartialUpdate {
  private LocalDateTime start;
  private LocalDateTime end;
}
