package com.malex.interceptor.controller.comment;

import com.malex.interceptor.controller.comment.v1.CommentV1;
import com.malex.interceptor.controller.comment.v2.CommentV2;
import com.malex.interceptor.webservice.comment.CommentRestClient;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

  private final CommentRestClient restClient;

  public CommentController(CommentRestClient restClient) {
    this.restClient = restClient;
  }

  /*
  * MIME type versioning
  *
  * MIME Types:
     JSON:API has been properly registered with the IANA.
     Its media type designation is application/vnd.api+json.
  *
  */
  @GetMapping(produces = "application/vnd.comments.api-v1+json")
  public ResponseEntity<List<CommentV1>> findAllV1() {
    return ResponseEntity.ok(restClient.findAllV1());
  }

  @GetMapping(produces = "application/vnd.comments.api-v2+json")
  public ResponseEntity<CommentV2> findAllV2() {
    return ResponseEntity.ok(restClient.findAllV2());
  }
}
