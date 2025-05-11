package com.malex.parallel_execution_of_tasks_without_waiting.sender;

import static com.malex.parallel_execution_of_tasks_without_waiting.controller.ApiRestController.*;

public interface MessageSender {
  void send(Message message);
}
