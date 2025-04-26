package com.malex.listener;

import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OnDisconnectedListener {

  public DisconnectListener onDisconnected() {
    return client -> {
      log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
    };
  }
}
