package com.malex.parallel_execution_of_tasks_without_waiting.service;

import static com.malex.parallel_execution_of_tasks_without_waiting.controller.ApiRestController.*;

import com.malex.parallel_execution_of_tasks_without_waiting.sender.MessageSender;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendInParallelService {

  private final ExecutorService runAsyncTasksElasticExecutor;

  private final List<MessageSender> messageSenders;

  /*
   * [pool-1-thread-3] c.m.p.sender.ViberSender                 : Viber send - Hello!
   * [pool-1-thread-1] c.m.p.sender.EmailSender                 : Email send - Hello!
   * [pool-1-thread-2] c.m.p.sender.TelegramSeder               : Telegram send - Hello!
   */
  public void sendMessageToSeveralTargets(Message message) {
    messageSenders.forEach(
        messageSender ->
            CompletableFuture.runAsync(
                () -> messageSender.send(message), runAsyncTasksElasticExecutor));
  }
}
