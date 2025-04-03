package com.malex.controller;

import com.malex.controller.dto.Post;
import com.malex.post.PostRestClient;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/posts")
public class PostController {

  private final PostRestClient postRestClient;

  public PostController(PostRestClient postRestClient) {
    this.postRestClient = postRestClient;
  }

  @GetMapping()
  public ResponseEntity<List<Post>> findPosts() {
    return ResponseEntity.ok(postRestClient.findAllPosts());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> findPostById(@PathVariable Integer id) {
    return ResponseEntity.ok(postRestClient.findPostById(id));
  }
}
