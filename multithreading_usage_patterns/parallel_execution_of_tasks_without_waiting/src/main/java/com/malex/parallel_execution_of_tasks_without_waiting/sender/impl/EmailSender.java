package com.malex.parallel_execution_of_tasks_without_waiting.sender.impl;

import com.malex.parallel_execution_of_tasks_without_waiting.controller.ApiRestController;
import com.malex.parallel_execution_of_tasks_without_waiting.sender.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailSender implements MessageSender {

  @Override
  public void send(ApiRestController.Message message) {
    log.info("Email send - {}", message.text());
  }
}
