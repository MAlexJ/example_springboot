package com.malex.interceptor.post;

import com.malex.interceptor.post.webclient.PostClient;
import com.malex.interceptor.post.webclient.TestPostClient;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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

  private final PostClient webclient;

  private final TestPostClient testWebclient;

  public PostController(PostClient webclient, TestPostClient testWebclient) {
    this.webclient = webclient;
    this.testWebclient = testWebclient;
  }

  @GetMapping
  public ResponseEntity<List<Post>> findAll() {
    return ResponseEntity.ok(webclient.findAll());
  }

  @GetMapping("/test-api")
  public ResponseEntity<List<Post>> test() {
    return ResponseEntity.ok(testWebclient.testApi());
  }

  @GetMapping("/internal")
  public ResponseEntity<List<Post>> internal() {
    int count = counter.incrementAndGet();
    LOG.info(" >>> Internal API: " + count);
    if (count % 3 != 0) {
      return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
    return ResponseEntity.ok(webclient.findAll());
  }
}
