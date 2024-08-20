package com.malex.rabbit_amqp.consumer;

import com.malex.rabbit_amqp.event.MessageEvent;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = "${rabbitmq.dl.queue}", id = "dlListener")
public class DeadLetterRabbitMqConsumer {

  @RabbitHandler
  public void onFail(MessageEvent message, @Header("x-death") Map<String, List<?>> xdeath) {
    log.info("Fail message: {} with information: {} ", message, xdeath);
  }
}
