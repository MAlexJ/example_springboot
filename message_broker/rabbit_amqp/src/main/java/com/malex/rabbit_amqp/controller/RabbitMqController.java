package com.malex.rabbit_amqp.controller;

import com.malex.rabbit_amqp.event.MessageEvent;
import com.malex.rabbit_amqp.producer.RabbitMqProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/messages")
@RequiredArgsConstructor
public class RabbitMqController {

  private final RabbitMqProducer producer;

  @PostMapping
  public ResponseEntity<MessageEvent> post(@RequestBody MessageEvent event) {
    producer.publish(event);
    return ResponseEntity.ok(event);
  }
}
