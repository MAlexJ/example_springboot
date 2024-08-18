package com.malex.rabbit_amqp.consumer;

import com.malex.rabbit_amqp.service.RabbitMqService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMqConsumer {

  private final RabbitMqService service;

  /*
   * Note: @RabbitListener can have only one of 'queues', 'queuesToDeclare', or 'bindings'
   * link: https://www.youtube.com/watch?v=dN60NUAZF7U
   */
  @RabbitListener(
      bindings =
          @QueueBinding(
              value = @Queue,
              exchange = @Exchange(value = "${rabbitmq.exchange.name}", type = ExchangeTypes.TOPIC),
              key = "${rabbitmq.routing.key}"))
  //  or @RabbitListener(queues = "${rabbitmq.queue.name}")
  public void consume(String message) {
    service.process(message);
  }
}
