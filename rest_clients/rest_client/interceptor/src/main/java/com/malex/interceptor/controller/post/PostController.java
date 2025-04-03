package com.malex.interceptor.controller.post;

import com.malex.interceptor.webservice.post.InternalPostWebClient;
import com.malex.interceptor.webservice.post.PostRestClient;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

  private static final Logger LOG = Logger.getLogger(PostController.class.getName());

  private final AtomicInteger counter = new AtomicInteger();

  private final PostRestClient restClient;

  private final InternalPostWebClient internalPostWebClient;

  public PostController(PostRestClient webclient, InternalPostWebClient internalPostWebClient) {
    this.restClient = webclient;
    this.internalPostWebClient = internalPostWebClient;
  }

  @GetMapping
  public ResponseEntity<List<Post>> findAll() {
    return ResponseEntity.ok(restClient.findAll());
  }

  @GetMapping("/test-api")
  public ResponseEntity<List<Post>> test() {
    return ResponseEntity.ok(internalPostWebClient.testApi());
  }

  @GetMapping("/internal")
  public ResponseEntity<List<Post>> internal() {
    int count = counter.incrementAndGet();

    if (LOG.isLoggable(Level.INFO)) LOG.info(" >>> Internal API: " + count);

    if (count % 3 != 0) {
      return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    return ResponseEntity.ok(restClient.findAll());
  }
}
