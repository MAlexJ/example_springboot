package com.malex.controller;

import com.malex.dto.EventDto;
import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventTimeRestController {

  /*
   * Format:
   * -------------------
   * 2025-04-08 = Date
   * T = Separator
   * 23:26:41 = Time (HH:mm:ss)
   * Z = UTC time zone (same as +00:00)
   */
  @GetMapping("/event")
  public ResponseEntity<EventDto> event() {
    var now = LocalDateTime.now();
    return ResponseEntity.ok(new EventDto(now, now, now));
  }
}
