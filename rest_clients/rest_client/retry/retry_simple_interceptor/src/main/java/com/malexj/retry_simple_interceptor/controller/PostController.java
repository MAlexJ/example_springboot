package com.malexj.retry_simple_interceptor.controller;

import com.malexj.retry_simple_interceptor.controller.dto.Post;
import com.malexj.retry_simple_interceptor.webservice.InternalPostWebClient;
import com.malexj.retry_simple_interceptor.webservice.PostRestClient;
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
