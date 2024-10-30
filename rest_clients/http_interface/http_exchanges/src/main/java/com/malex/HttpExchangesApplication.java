package com.malex;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malex.http_exchange.PostExchangeInterface;

/*
 * Declarative HTTP services using Java interfaces
 */
@SpringBootApplication
public class HttpExchangesApplication {

  public static void main(String[] args) {
    SpringApplication.run(HttpExchangesApplication.class, args);
  }

  @RestController
  @RequestMapping("/v1/api")
  @RequiredArgsConstructor
  public static class RestApiController {

    private final PostExchangeInterface webExchangeInterface;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> findAll() {
      return ResponseEntity.ok(webExchangeInterface.findAll());
//      return ResponseEntity.ok(Collections.EMPTY_LIST);
    }
  }

  public record Post(Long userId, Long id, String title, String body) {}
}
