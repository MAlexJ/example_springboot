package com.malex.configuration;

import static com.malex.listener.task.TaskListener.TASK_EVENT_NAME;
import static com.malex.listener.user.UserListener.USER_EVENT_NAME;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.protocol.JacksonJsonSupport;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.malex.listener.OnConnectedListener;
import com.malex.listener.OnDisconnectedListener;
import com.malex.listener.task.TaskListener;
import com.malex.listener.task.event.TaskEventRequest;
import com.malex.listener.user.UserListener;
import com.malex.listener.user.event.UserEventRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebsocketConfiguration {

  @Value("${socket-server.host}")
  private String host;

  @Value("${socket-server.port}")
  private Integer port;

  @Bean
  public com.corundumstudio.socketio.Configuration socketIOConfig() {
    var config = new com.corundumstudio.socketio.Configuration();
    config.setHostname(host);
    config.setPort(port);
    config.setJsonSupport(new JacksonJsonSupport(new JavaTimeModule()));
    return config;
  }

  @Bean
  public SocketIOServer socketIOServer(
          com.corundumstudio.socketio.Configuration socketIOConfig,
          OnConnectedListener onConnectedListener,
          OnDisconnectedListener onDisconnectedListener,
          UserListener userListener,
          TaskListener taskListener) {
    var server = new SocketIOServer(socketIOConfig);

    server.addConnectListener(onConnectedListener.onConnected());
    server.addDisconnectListener(onDisconnectedListener.onDisconnected());
    server.addEventListener(
        USER_EVENT_NAME, UserEventRequest.class, userListener.onEventReceived());
    server.addEventListener(
        TASK_EVENT_NAME, TaskEventRequest.class, taskListener.onEventReceived());

    return server;
  }
}
