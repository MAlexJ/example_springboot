package com.malex.rabbit_amqp.consumer;

import java.util.List;
import java.util.Map;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import com.malex.rabbit_amqp.event.MessageEvent;

@Service
public class DLQListener {


    @RabbitListener(queues = "${rabbitmq.dl.queue}")
    public void onFail(MessageEvent message, @Header("x-death") Map<String, List<?>> xdeath) {
        System.out.println("Fail message :" + message
                + "with information : " + xdeath);
    }

}
