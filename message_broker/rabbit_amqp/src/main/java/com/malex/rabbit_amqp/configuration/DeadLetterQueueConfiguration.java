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
 * Dead Letter queue configuration
 */
@Configuration
public class DeadLetterQueueConfiguration {

  @Value("${rabbitmq.dl.queue}")
  private String deadLetterQueue;

  @Value("${rabbitmq.dl.exchange}")
  private String deadLetterExchange;

  @Value("${rabbitmq.dl.routing.key}")
  private String deadLetterRoutingKey;

  @Bean
  @Qualifier("rabbitmq.deadLetterQueue")
  public Queue deadLetterQueue() {
    return QueueBuilder.durable(deadLetterQueue).build();
  }

  @Bean
  @Qualifier("rabbitmq.deadLetterExchange")
  public DirectExchange deadLetterExchange() {
    return new DirectExchange(deadLetterExchange);
  }

  @Bean
  public Binding deadLetterBinding(
      @Qualifier("rabbitmq.deadLetterQueue") Queue queue,
      @Qualifier("rabbitmq.deadLetterExchange") DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(deadLetterRoutingKey);
  }
}
