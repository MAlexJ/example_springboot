package com.malex.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/events")
public class RestApiController {

  @PostMapping("/date")
  public ResponseEntity<Date> date(@RequestParam("date") Date date) {
    log.info("Received request for date: {}", date);
    return ResponseEntity.ok(date);
  }

  @PostMapping("/local-date")
  public ResponseEntity<LocalDate> localDate(@RequestParam("localDate") LocalDate localDate) {
    log.info("Received request for localDate: {}", localDate);
    return ResponseEntity.ok(localDate);
  }

  @PostMapping("/local-date-time")
  public ResponseEntity<LocalDateTime> dateTime(
      @RequestParam("localDateTime") LocalDateTime localDateTime) {
    log.info("Received request for localDateTime: {}", localDateTime);
    return ResponseEntity.ok(localDateTime);
  }

  /**
   * Combination application level configuration: DateTimeConfig and request level: @DateTimeFormat
   */
  @PostMapping("/custom")
  public ResponseEntity<Date> customDateTimeFormat(
      @RequestParam("date") @DateTimeFormat(pattern = "dd.MM.yyyy") Date date) {
    log.info("Custom date: {}", date);
    return ResponseEntity.ok(date);
  }
}
