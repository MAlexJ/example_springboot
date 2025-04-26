package com.malex;

import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class SendAckDataApplication implements CommandLineRunner {

  private final SocketIOServer server;

  public static void main(String[] args) {
    SpringApplication.run(SendAckDataApplication.class, args);
  }

  @Override
  public void run(String... args) {
    server.startAsync();
  }

  @PreDestroy
  public void stopServer() {
    if (server != null) {
      server.stop();
    }
  }
}
