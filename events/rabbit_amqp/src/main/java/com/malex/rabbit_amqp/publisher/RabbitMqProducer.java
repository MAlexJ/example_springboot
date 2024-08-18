package com.malex.rabbit_amqp.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMqProducer {

  @Value("${rabbitmq.exchange.name}")
  public String exchange;

  @Value("${rabbitmq.routing.key}")
  public String routingKey;

  public final RabbitTemplate rabbitTemplate;

  public void send(String message) {
    log.info("send message: {}", message);
    rabbitTemplate.convertAndSend(exchange, routingKey, message);
  }
}
