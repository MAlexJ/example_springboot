package com.malex.listener.user;

import com.corundumstudio.socketio.listener.DataListener;
import com.malex.listener.user.event.UserEventRequest;
import com.malex.listener.user.event.UserEventResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserListener {

  public static final String USER_EVENT_NAME = "user_event";

  public DataListener<UserEventRequest> onEventReceived() {
    return (client, request, ackSender) -> {
      log.info("Received message - {}, isAckRequested - {} ", request, ackSender.isAckRequested());

      if (ackSender.isAckRequested())
        ackSender.sendAckData(new UserEventResponse("Alex", request.userId()));
    };
  }
}
