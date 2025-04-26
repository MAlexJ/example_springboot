package com.malex.listener;

import com.corundumstudio.socketio.listener.ConnectListener;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OnConnectedListener {

  public ConnectListener onConnected() {
    return (client) -> {
      HttpHeaders httpHeaders = client.getHandshakeData().getHttpHeaders();

      log.info(
          "Client[{}] - Connected to socket with headers: {}",
          client.getSessionId().toString(),
          httpHeaders);

      //      String room = client.getHandshakeData().getSingleUrlParam("room");
      //      client.joinRoom(room);

      log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
    };
  }
}
