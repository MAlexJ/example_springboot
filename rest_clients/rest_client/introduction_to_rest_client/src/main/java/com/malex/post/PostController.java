package com.malex.post;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/v1/api/posts")
public class PostController {

  @Value("${webservice.client.url}")
  private String baseUrl;

  @Value("${webservice.client.path.posts.findAll}")
  private String findAllPostsUri;

  @Value("${webservice.client.path.posts.byId}")
  private String findPostByIdUri;

  private final RestClient restClient;

  public PostController(RestClient restClient) {
    this.restClient = restClient;
  }

  @GetMapping()
  public ResponseEntity<List<Post>> findPosts() {
    var uriComponents = UriComponentsBuilder.fromUriString(baseUrl).path(findAllPostsUri).build();
    var uri = uriComponents.toUri();
    var posts =
        restClient.get().uri(uri).retrieve().body(new ParameterizedTypeReference<List<Post>>() {});
    return ResponseEntity.ok(posts);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> findPostById(@PathVariable Long id) {
    var uri = UriComponentsBuilder.fromUriString(baseUrl).path(findPostByIdUri).build(id);
    var post = restClient.get().uri(uri).retrieve().body(Post.class);
    return ResponseEntity.ok(post);
  }
}
