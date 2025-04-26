package com.malex.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.malex.service.event.MessageEventRequest;
import com.malex.service.event.MessageEventResponse;
import com.malex.service.event.MessageType;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SocketService {

  public void sendRoomMessage(
      String room, String eventName, SocketIOClient senderClient, String message) {

    for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
      if (!client.getSessionId().equals(senderClient.getSessionId())) {
        client.sendEvent(eventName, new MessageEventRequest(MessageType.SERVER, message, room));
      }
    }
  }

  public void sendMessage(String eventName, SocketIOClient senderClient, String message) {
    log.info("Sending message to room - {}", message);
    senderClient.sendEvent(
        eventName, new MessageEventResponse(UUID.randomUUID().toString(), message));
  }
}
