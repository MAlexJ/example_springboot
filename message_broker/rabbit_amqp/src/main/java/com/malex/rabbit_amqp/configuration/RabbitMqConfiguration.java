package com.malex.rabbit_amqp.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.util.ErrorHandler;

@Slf4j
@EnableRabbit
@Configuration
public class RabbitMqConfiguration {

  @Value("${rabbitmq.queue}")
  private String queue;

  @Value("${rabbitmq.exchange}")
  private String exchange;

  @Value("${rabbitmq.routing.key}")
  private String routingKey;

  @Value("${rabbitmq.dl.queue}")
  private String deadLetterQueue;

  @Value("${rabbitmq.dl.exchange}")
  private String deadLetterExchange;

  @Value("${rabbitmq.dl.routing.key}")
  private String deadLetterRoutingKey;

  @Value("${rabbitmq.username}")
  private String username;

  @Value("${rabbitmq.password}")
  private String password;

  @Value("${rabbitmq.host}")
  private String host;

  @Value("${rabbitmq.virtualhost}")
  private String virtualHost;

  /*
   * Specify the timeout in milliseconds to be used when waiting for a reply Message when using one
   * of the sendAndReceive methods.
   * The default value is defined as DEFAULT_REPLY_TIMEOUT.
   */
  @Value("${rabbitmq.reply.timeout}")
  private Integer replyTimeout;

  /*
   * the minimum number of consumers to create.
   */
  @Value("${rabbitmq.concurrent.consumers.min}")
  private Integer minConcurrentConsumers;

  @Value("${rabbitmq.concurrent.consumers.max}")
  private Integer maxConcurrentConsumers;

  /*
   * Dead Letter queue configuration
   */
  @Bean
  @Qualifier("rabbitmq.deadLetterQueue")
  public Queue deadLetterQueue() {
    var deadLetter = new HashMap<String, Object>();
    deadLetter.put("x-dead-letter-exchange", "rabbitmq.deadLetterExchange");
    deadLetter.put("x-max-length", 1000);
    return new Queue(deadLetterQueue, true, false, false, deadLetter);
  }

  /*
   * Dead Letter exchange configuration
   */
  @Bean
  @Qualifier("rabbitmq.deadLetterExchange")
  public DirectExchange deadLetterExchange() {
    return new DirectExchange(deadLetterExchange);
  }

  /*
   * Dead Letter binding configuration
   */
  @Bean
  public Binding deadLetterBinding(
      @Qualifier("rabbitmq.deadLetterQueue") Queue queue,
      @Qualifier("rabbitmq.deadLetterExchange") DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(deadLetterRoutingKey);
  }

  // Queue configuration
  @Bean
  @Qualifier("rabbitmq.queue")
  public Queue queue() {
    return new Queue(queue, true);
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

  @Bean
  public MessageConverter jsonMessageConverter() {
    ObjectMapper objectMapper = new ObjectMapper();
    return new Jackson2JsonMessageConverter(objectMapper);
  }

  @Bean
  public ConnectionFactory connectionFactory() {
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
    connectionFactory.setVirtualHost(virtualHost);
    connectionFactory.setHost(host);
    connectionFactory.setUsername(username);
    connectionFactory.setPassword(password);
    return connectionFactory;
  }

  @Bean
  public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setDefaultReceiveQueue(deadLetterQueue);
    rabbitTemplate.setMessageConverter(jsonMessageConverter());
    rabbitTemplate.setReplyAddress(queue().getName());
    rabbitTemplate.setReplyTimeout(replyTimeout);
    rabbitTemplate.setUseDirectReplyToContainer(false);
    return rabbitTemplate;
  }

  @Bean
  public AmqpAdmin amqpAdmin() {
    return new RabbitAdmin(connectionFactory());
  }

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
    final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory());
    factory.setMessageConverter(jsonMessageConverter());
    factory.setConcurrentConsumers(minConcurrentConsumers);
    factory.setMaxConcurrentConsumers(maxConcurrentConsumers);
    factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
    factory.setAdviceChain(setRetries());
    return factory;
  }

  @Bean
  public ErrorHandler errorHandler() {
    return new ConditionalRejectingErrorHandler(new MyFatalExceptionStrategy());
  }

  public static class MyFatalExceptionStrategy
      extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {
    @Override
    public boolean isFatal(Throwable t) {
      if (t instanceof ListenerExecutionFailedException ex) {
        log.error(
            "Failed to process inbound message from queue {}; failed message: {}",
            ex.getFailedMessage().getMessageProperties().getConsumerQueue(),
            ex.getFailedMessage(),
            t);
      }
      return super.isFatal(t);
    }
  }

  @Bean("operationsInterceptor")
  public RetryOperationsInterceptor setRetries() {
    return RetryInterceptorBuilder.stateless()
        .maxAttempts(4)
        .backOffOptions(1000, 2, 10000)
        .recoverer(new RejectAndDontRequeueRecoverer())
        .build();
  }
}
