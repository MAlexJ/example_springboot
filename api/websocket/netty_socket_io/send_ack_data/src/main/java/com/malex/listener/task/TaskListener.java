package com.malex.listener.task;

import com.corundumstudio.socketio.listener.DataListener;
import com.malex.listener.task.event.TaskEventRequest;
import com.malex.listener.task.event.TaskEventResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskListener {

  public static final String TASK_EVENT_NAME = "task_event";

  public DataListener<TaskEventRequest> onEventReceived() {
    return (client, request, ackSender) -> {
      log.info("Received message - {}, isAckRequested - {} ", request, ackSender.isAckRequested());

      if (ackSender.isAckRequested()) {
        List<TaskEventResponse> tasks =
            List.of(new TaskEventResponse("data1"), new TaskEventResponse("data2"));
        ackSender.sendAckData(tasks);
      }
    };
  }
}
