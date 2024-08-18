package com.malex.rabbit_amqp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

  @Value("${rabbitmq.queue.name}")
  public String queue;

  @Value("${rabbitmq.exchange.name}")
  public String exchange;

  @Value("${rabbitmq.routing.key}")
  public String routingKey;

  // 1. queue configuration
  @Bean
  public Queue queue() {
    return new Queue(queue);
  }

  // 2. exchange configuration
  @Bean
  public Exchange exchange() {
    return new TopicExchange(exchange);
  }

  // 3. binding between queue and exchange using routing key
  @Bean
  public Binding binding(Queue queue, Exchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
  }

  // 4. ConnectionFactory configuration - automatically by Spring
  //  @Bean
  //  public ConnectionFactory connectionFactory() {
  //    CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
  //    connectionFactory.setVirtualHost(virtualHost);
  //    connectionFactory.setHost(host);
  //    connectionFactory.setUsername(username);
  //    connectionFactory.setPassword(password);
  //    return connectionFactory;
  //  }

  // 5. RabbitTemplate configuration - automatically by Spring
  //  @Bean
  //  public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
  //    var rabbitTemplate = new RabbitTemplate(connectionFactory);
  //    rabbitTemplate.setDefaultReceiveQueue(queueName);
  //    rabbitTemplate.setMessageConverter(jsonMessageConverter());
  //    rabbitTemplate.setReplyAddress(queue().getName());
  //    rabbitTemplate.setReplyTimeout(replyTimeout);
  //    rabbitTemplate.setUseDirectReplyToContainer(false);
  //    return rabbitTemplate;
  //  }

  // 6. RabbitAdmin configuration - automatically by Spring
  //  @Bean
  //  public AmqpAdmin amqpAdmin() {
  //    return new RabbitAdmin(connectionFactory());
  //  }
}
