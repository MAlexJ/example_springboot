package com.malex.rabbit_amqp.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Queue configuration
 */
@Configuration
public class EventMessageQueueConfiguration {

  @Value("${rabbitmq.queue}")
  private String queue;

  @Value("${rabbitmq.exchange}")
  private String exchange;

  @Value("${rabbitmq.routing.key}")
  private String routingKey;

  @Value("${rabbitmq.dl.exchange}")
  private String deadLetterExchange;

  @Value("${rabbitmq.dl.routing.key}")
  private String deadLetterRoutingKey;

  @Bean
  @Qualifier("rabbitmq.queue")
  public Queue queue() {
    return QueueBuilder.durable(queue)
        .withArgument("x-dead-letter-exchange", deadLetterExchange)
        .withArgument("x-dead-letter-routing-key", deadLetterRoutingKey)
        .ttl(2000)
        .build();
  }

  @Bean
  @Qualifier("rabbitmq.exchange")
  public DirectExchange exchange() {
    return new DirectExchange(exchange);
  }

  @Bean
  public Binding binding(
      @Qualifier("rabbitmq.queue") Queue queue,
      @Qualifier("rabbitmq.exchange") DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(routingKey);
  }
}
