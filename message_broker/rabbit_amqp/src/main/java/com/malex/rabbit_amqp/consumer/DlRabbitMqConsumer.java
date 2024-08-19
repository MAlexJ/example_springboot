package com.malex.rabbit_amqp.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@RabbitListener(queues = "${rabbitmq.queue}", id = "dlListener")
@Component
public class DlRabbitMqConsumer {}
