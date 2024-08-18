package com.malex.rabbit_amqp.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMqConsumer {

  @RabbitListener(queues = "${rabbitmq.queue.name}")
  public void consume(String message) {
    log.info("consume: {}", message);
  }
}
