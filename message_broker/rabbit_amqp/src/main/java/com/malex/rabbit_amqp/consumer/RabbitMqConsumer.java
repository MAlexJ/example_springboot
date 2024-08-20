package com.malex.rabbit_amqp.consumer;

import com.malex.rabbit_amqp.event.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = "${rabbitmq.queue}", id = "listener")
public class RabbitMqConsumer {

  @RabbitHandler
  public void receiver(MessageEvent event) {
    log.info("Rabbit sender object {} received", event);
    if (event.id() == 7) {
      throw new RuntimeException("occurs error!");
    }
  }
}
