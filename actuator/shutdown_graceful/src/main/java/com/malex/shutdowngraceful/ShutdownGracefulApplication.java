package com.malex.shutdowngraceful;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ShutdownGracefulApplication {

  /*
   * In console logs:
   * tomcat.GracefulShutdown : Commencing graceful shutdown. Waiting for active requests to complete
   * tomcat.GracefulShutdown  : Graceful shutdown complete
   */
  public static void main(String[] args) {
    SpringApplication.run(ShutdownGracefulApplication.class, args);
  }

  @RestController
  @RequestMapping("/v1/api")
  public static class RestApiController {

    Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @GetMapping("/info")
    public ResponseEntity<String> info() {
      logger.info("HTTP request: info");
      return ResponseEntity.ok("info");
    }
  }
}
