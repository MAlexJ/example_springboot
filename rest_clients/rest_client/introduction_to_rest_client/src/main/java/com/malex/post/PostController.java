package com.malex.post;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/posts")
public class PostController {

  private final PostClient postClient;

  public PostController(PostClient postClient) {
    this.postClient = postClient;
  }

  @GetMapping()
  public ResponseEntity<List<Post>> findPosts() {
    return ResponseEntity.ok(postClient.findAllPosts());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> findPostById(@PathVariable Integer id) {
    return ResponseEntity.ok(postClient.findPostById(id));
  }
}
