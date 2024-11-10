package com.malex.post;

import com.malex.comments.Comment;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/posts")
@RequiredArgsConstructor
public class PostController {

  private final PostClient postClient;

  @GetMapping
  public ResponseEntity<List<Post>> getAllPosts() {
    return ResponseEntity.ok(postClient.findAllPost());
  }

  @GetMapping("/{id}/comments")
  public ResponseEntity<List<Comment>> findPostComments(@PathVariable Long id) {
    Objects.requireNonNull(id, "id must not be null");
    return ResponseEntity.ok(postClient.findAllCommentsForPostById(id));
  }
}
