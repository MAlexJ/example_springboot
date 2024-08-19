### RabbitMQ

RabbitMQ is a reliable and mature messaging and streaming broker,
tutorial: https://www.youtube.com/watch?v=0--Ll3WHMTQ

RabbitMQ is an extremely popular open-source Message Broker used for building message-based systems.
Although RabbitMQ supports multiple protocols, the most commonly used is AMQP.

AMQP 0–9–1 (Advanced Message Queuing Protocol) is a messaging protocol that enables conforming client applications to
communicate with conforming messaging middleware brokers. It’s an application layer protocol that transmits data in
binary format. In this application, data is sent as frames.

link: https://medium.com/cwan-engineering/rabbitmq-concepts-and-best-practices-aa3c699d6f08

#### CloudAMQP RabbitMQ provider

link: https://api.cloudamqp.com

plan: 'Little Lemur'

* Open Connections: 0 of 20
* Max Idle Queue Time: 28 days
* Queues: 2 of 150
* Messages: 7 of 1 000 000
* Queue Length: 1 of 10 000

#### Switching to CloudAMQP

I had the same error when I switched to Cloud AMQP.

link: https://stackoverflow.com/questions/72248024/switching-to-cloudamqp-gives-com-rabbitmq-client-shutdownsignalexception

As you mentioned, the virtual host was missing from the properties:

```
spring.rabbitmq.virtual-host=u_name

spring.rabbitmq.host=.....
spring.rabbitmq.username=u_name
spring.rabbitmq.password=........
spring.rabbitmq.port=5672
```

### Configurations RabbitMQ with Spring Boot

RabbitMQ is an open-source message broker that allows applications to communicate with each other.
It’s a very popular AMQP(Advance Message Queuing Protocol) supported by most programming languages.RabbitMQ is an
open-source message broker that allows applications to communicate with each other. It’s a very popular AMQP(Advance
Message Queuing Protocol) supported by most programming languages.

link: https://medium.com/@ravinduperera1229/rabbitmq-with-spring-boot-1935ed42da6a

RabbitMQ Components:

### Configurations RabbitMQ Spring tutorial

link: https://habr.com/ru/articles/262069/

### BeanDefinitionOverrideException in Spring Boot

The Spring Boot 2.1 upgrade surprised people with unexpected occurrences of the BeanDefinitionOverrideException. It can
confuse developers and make them wonder what happened to the bean overriding behavior in Spring.

link: https://www.baeldung.com/spring-boot-bean-definition-override-exception

### Time-to-Live and Expiration

link: https://www.rabbitmq.com/docs/ttl

With RabbitMQ, you can set a TTL (time-to-live) argument or policy for messages and queues.
As the name suggests, TTL specifies the time period that the messages and queues "live for".

Message TTL determines how long messages can be retained in a queue.
If the retention period of a message in a queue exceeds the message TTL of the queue, the message expires and is
discarded.

#### Springboot TTL sample

There are different ways where RabbitMQ deletes the messages. Some of them are:

After Ack from consumer
Time-to-live(TTL) for that Queue reached.
Time-to-live(TTL) for messages on that Queue reached.

```
rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE,
                    RabbitMqConfig.RK,
                    String.valueOf(body),
                    message -> {
              message.getMessageProperties().setExpiration(String.valueOf(1000));
                        return message;
                    });
```

or

```
@Autowired
   private RabbitTemplate   rabbit;

   @Autowired
   private MessageConverter jsonMessageConverter;

   public void produce() {

      rabbit.setExchange("My.Exchange");
      rabbit.setRoutingKey("R.K");
      rabbit.setMessageConverter(jsonMessageConverter);
      MessageProperties props = new MessageProperties();
      props.setExpiration(Long.toString(expiration));
      Message toSend = new Message(message.toString().getBytes(), props);
      rabbit.send(toSend);
   }
```

link: https://stackoverflow.com/questions/45986687/how-to-set-per-message-ttl-with-rabbittemplate
