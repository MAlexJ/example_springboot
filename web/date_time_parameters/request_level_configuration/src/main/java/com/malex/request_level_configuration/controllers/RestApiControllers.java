package com.malex.request_level_configuration.controllers;

import com.malex.request_level_configuration.dto.Event;
import com.malex.request_level_configuration.dto.EventStartEndDatePartialUpdate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/events")
@RequiredArgsConstructor
public class RestApiControllers {

  private final AtomicInteger counter = new AtomicInteger(3);

  private final List<Event> events;

  @PostMapping("/date")
  public ResponseEntity<Date> date(
      @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
    log.info("Received request for date: {}", date);
    return ResponseEntity.ok(date);
  }

  @PostMapping("/local-date")
  public ResponseEntity<LocalDate> localDate(
      @RequestParam("localDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
          LocalDate localDate) {
    log.info("Received request for localDate: {}", localDate);
    return ResponseEntity.ok(localDate);
  }

  @PostMapping("/local-date-time")
  public ResponseEntity<LocalDateTime> dateTime(
      @RequestParam("localDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime localDateTime) {
    log.info("Received request for localDateTime: {}", localDateTime);
    return ResponseEntity.ok(localDateTime);
  }

  @CrossOrigin
  @PostMapping
  public ResponseEntity<Event> createEvent(@RequestBody Event event) {
    int id = counter.incrementAndGet();
    log.info("Received request for createEvent: {}", event);
    event.setId(id);
    events.add(event);
    return ResponseEntity.ok(event);
  }

  @CrossOrigin
  @GetMapping
  public ResponseEntity<List<Event>> findAllEvents() {
    log.info("Received request for findAllEvents");
    return ResponseEntity.ok(events);
  }

  @CrossOrigin
  @PutMapping
  public ResponseEntity<String> put(@RequestBody String req) {
    log.info("Received request for put: {}", req);
    return ResponseEntity.ok(req);
  }

  /*
   * When a client needs to replace an existing Resource entirely, they can use PUT. When they’re
   * doing a partial update, they can use HTTP PATCH.
   *
   * PATCH:
   * link: https://stackoverflow.com/questions/28459418/use-of-put-vs-patch-methods-in-rest-api-real-life-scenarios
   *
   * <p>parameters:
   * id - event id
   *
   * json {
   *  start - new event start date
   *  end - new event end date
   * }
   */
  @CrossOrigin
  @PatchMapping("/{id}")
  public ResponseEntity<Event> partialUpdate(
      @PathVariable int id, @RequestBody EventStartEndDatePartialUpdate partialUpdate) {
    log.info("Received request for patch: {}", partialUpdate);
    return events.stream()
        .filter(event -> event.getId() == id)
        .findFirst()
        .map(
            event -> {
              // use side effect: change object by link
              event.setStart(partialUpdate.getStart());
              event.setEnd(partialUpdate.getEnd());
              return ResponseEntity.ok(event);
            })
        .orElse(ResponseEntity.notFound().build());
  }

  @CrossOrigin
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable int id) {
    log.info("Delete event by id: {}", id);
    return events.stream()
        .filter(event -> event.getId() == id)
        .findFirst()
        .map(events::remove)
        .filter(Boolean::valueOf)
        .map(r -> new ResponseEntity<Void>(HttpStatus.OK))
        .orElse(ResponseEntity.notFound().build());
  }
}
