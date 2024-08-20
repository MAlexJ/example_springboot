package com.malex.rabbit_amqp.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.malex.rabbit_amqp.event.MessageEvent;

@Slf4j
@Component
@RabbitListener(queues = "${rabbitmq.dl.queue}", id = "dlListener")
public class DeadLetterRabbitMqConsumer {

  @RabbitHandler
  public void receiver(Message data) {
    log.error("Rabbit dl sender object - {} received", data);
  }
}
