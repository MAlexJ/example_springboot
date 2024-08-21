### Spring for Apache Kafka

The Spring for Apache Kafka (spring-kafka) project applies core Spring concepts to the development of Kafka-based
messaging solutions. It provides a "template" as a high-level abstraction for sending messages. It also provides support
for Message-driven POJOs with @KafkaListener annotations and a "listener container". These libraries promote the use of
dependency injection and declarative. In all of these cases, you will see similarities to the JMS support in the Spring
Framework and RabbitMQ support in Spring AMQP.

Features

* KafkaTemplate
* KafkaMessageListenerContainer
* @KafkaListener
* KafkaTransactionManager
* spring-kafka-test jar with embedded kafka server

link: https://spring.io/projects/spring-kafka

### Project configuration

add properties to `.env` file:

```
RABBITMQ_HOST=cow.rmq2.cloudamqp.com
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=......
RABBITMQ_PASSWORD=........
RABBITMQ_VIRTUAL_HOST=......
```