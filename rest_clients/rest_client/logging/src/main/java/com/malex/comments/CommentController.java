package com.malex.comments;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class CommentController {

  private final CommentClient commentClient;

  @GetMapping("/comments")
  public ResponseEntity<List<Comment>> findAll() {
    return ResponseEntity.ok(commentClient.findAllComments());
  }
}
