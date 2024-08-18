package com.malex.rabbit_amqp.controller;

import com.malex.rabbit_amqp.publisher.RabbitMqProducer;
import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/messages")
@RequiredArgsConstructor
public class MessageRestController {

  private final RabbitMqProducer producer;

  @PostMapping
  private ResponseEntity<String> getMessages(@RequestBody Message message) {
    producer.send(message.name());
    return ResponseEntity.ok("");
  }

  private record Message(String name) implements Serializable {}
}
