package com.malex.post;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PostClient {

  private static final Logger LOG = Logger.getLogger(PostClient.class.getName());

  private final RestClient restClient;

  @Value("${webservice.client.url}")
  private String baseUrl;

  @Value("${webservice.client.path.posts.findAll}")
  private String findAllPostsUri;

  @Value("${webservice.client.path.posts.byId}")
  private String findPostByIdUri;

  public PostClient(@Value("${webservice.client.url}") String url, RestClient.Builder builder) {
    // todo: WTF
    if (Objects.isNull(baseUrl)) {
      LOG.warning("baseUrl is null");
    }

    this.restClient = builder.baseUrl(url).build();
  }

  public List<Post> findAllPosts() {
    return restClient
        .get()
        .uri(findAllPostsUri)
        .retrieve()
        .body(new ParameterizedTypeReference<List<Post>>() {});
  }

  public Post findPostById(int id) {
    return restClient
        .get()
        .uri(uriBuilder -> uriBuilder.path(findPostByIdUri).build(id))
        .retrieve()
        .body(Post.class);
  }
}
