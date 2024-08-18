### RabbitMq

tutorial: https://www.youtube.com/watch?v=0--Ll3WHMTQ

#### Switching to CloudAMQP gives com.rabbitmq.client.ShutdownSignalException

link: https://stackoverflow.com/questions/72248024/switching-to-cloudamqp-gives-com-rabbitmq-client-shutdownsignalexception

I had the same error when I switched to Cloud AMQP.
As you mentioned, the virtual host was missing from the properties:

```
spring.rabbitmq.virtual-host=u_name

spring.rabbitmq.host=.....
spring.rabbitmq.username=u_name
spring.rabbitmq.password=........
spring.rabbitmq.port=5672
```

### Reference Documentation

* [Spring for RabbitMQ](https://docs.spring.io/spring-boot/docs/3.3.1/reference/htmlsingle/index.html#messaging.amqp)

### Guides

The following guides illustrate how to use some features concretely:

* [Messaging with RabbitMQ](https://spring.io/guides/gs/messaging-rabbitmq/)


