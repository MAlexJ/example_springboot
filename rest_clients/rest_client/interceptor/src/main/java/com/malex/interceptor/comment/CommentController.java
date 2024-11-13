package com.malex.interceptor.comment;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malex.interceptor.comment.v1.CommentV1;
import com.malex.interceptor.comment.v2.CommentV2;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

  private final CommentClient webclient;

  public CommentController(CommentClient webclient) {
    this.webclient = webclient;
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
    return ResponseEntity.ok(webclient.findAllV1());


  }  @GetMapping(produces = "application/vnd.comments.api-v2+json")
  public ResponseEntity<CommentV2> findAllV2() {
    return ResponseEntity.ok(webclient.findAllV2());
  }
}
