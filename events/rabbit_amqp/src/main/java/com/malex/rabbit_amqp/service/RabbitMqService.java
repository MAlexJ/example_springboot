package com.malex.rabbit_amqp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMqService {

  public void process(String message) {
    log.info("consume: {}", message);
  }
}
