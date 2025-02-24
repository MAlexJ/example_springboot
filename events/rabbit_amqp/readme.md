### RabbitMQ

RabbitMQ is a reliable and mature messaging and streaming broker,
tutorial: https://www.youtube.com/watch?v=0--Ll3WHMTQ

RabbitMQ is an extremely popular open-source Message Broker used for building message-based systems.
Although RabbitMQ supports multiple protocols, the most commonly used is AMQP.

AMQP 0–9–1 (Advanced Message Queuing Protocol) is a messaging protocol that enables conforming client applications to
communicate with conforming messaging middleware brokers. It’s an application layer protocol that transmits data in
binary format. In this application, data is sent as frames.

### RabbitMQ examples

link: https://github.com/MAlexJ/rabbitmq-examples

### Project configuration

###### Add properties to `.env` file:

```
RABBITMQ_HOST=cow.rmq2.cloudamqp.com
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=......
RABBITMQ_PASSWORD=........
RABBITMQ_VIRTUAL_HOST=......
```

###### Provider

* https://www.cloudamqp.com (Message Queues in the Cloud)

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
