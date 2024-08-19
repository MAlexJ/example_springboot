package com.malex.rabbit_amqp.producer;

import com.malex.rabbit_amqp.event.MessageEvent;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
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
    rabbitTemplate.convertAndSend(
        exchange,
        routingKey,
        event,
        message -> {
          var properties = message.getMessageProperties();
          log.info("Properties - {}", properties);
          /*
           * Set the message expiration.
           * This is a String property per the AMQP 0.9.1 spec.
           * For RabbitMQ, this is a String representation of the message time to live in milliseconds.
           */
          var oneDayInMillis = Long.toString(TimeUnit.DAYS.toMillis(1));
          properties.setExpiration(oneDayInMillis);

          /*
           * Enumeration for the message delivery mode. Can be persistent or non persistent.
           * Use the method 'toInt' to get the appropriate value that is used by the AMQP protocol instead of the ordinal()
           * value when passing into AMQP APIs.
           *
           * NON_PERSISTENT = 1
           * PERSISTENT = 2
           * default = -1
           */
          properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
          return message;
        });
  }
}
