package com.malex.rabbit_amqp.producer;

import com.malex.rabbit_amqp.event.MessageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMqProducer {


    @Value("${rabbitmq.exchange}")
    public String exchange;

    @Value("${rabbitmq.routing.key}")
    public String routingKey;

    public final RabbitTemplate rabbitTemplate;

  public void publish(MessageEvent event) {
    log.info("Publish event - {}", event);
    rabbitTemplate.convertAndSend(exchange, routingKey, event);
  }
}
