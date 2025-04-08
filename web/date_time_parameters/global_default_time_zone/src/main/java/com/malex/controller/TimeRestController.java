package com.malex.controller;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeRestController {

  /*
   * The timestamp you provided—2025-04-08T20:00:22.098+00:00—follows the ISO 8601 format,
   * which is a standard for date and time representation.
   *
   * Breakdown of the format:
   * -------------------------------------------------------
   * 2025-04-08      --> Date (YYYY-MM-DD)
   * T               --> Time delimiter
   * 20:00:22.098    --> Time (HH:mm:ss.SSS)
   * +00:00          --> Time zone offset from UTC (in this case, UTC itself)
   */
  @GetMapping("/time")
  public ResponseEntity<LocalDateTime> timeZone() {
    return ResponseEntity.ok(LocalDateTime.now());
  }
}
