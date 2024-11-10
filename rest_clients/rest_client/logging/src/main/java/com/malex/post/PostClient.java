package com.malex.post;

import com.malex.comments.Comment;
import com.malex.interceptor.RequestResponseLoginInterceptor;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PostClient {

  private final RestClient restClient;

  @Value("${webservice.client.path.posts.comments.findAllByPostId}")
  private String allCommentsForPostPath;

  @Value("${webservice.client.path.posts.findAll}")
  private String findAllPostsPath;

  public PostClient(
      @Value("${webservice.client.url}") String baseUrl,
      RestClient.Builder builder,
      RequestResponseLoginInterceptor interceptor) {
    this.restClient = builder.baseUrl(baseUrl).requestInterceptor(interceptor).build();
  }

  public List<Comment> findAllCommentsForPostById(Long id) {
    return restClient
        .get()
        .uri(uriBuilder -> uriBuilder.path(allCommentsForPostPath).build(id))
        .retrieve()
        .body(new ParameterizedTypeReference<>() {});
  }

  public List<Post> findAllPost() {
    return restClient
        .get()
        .uri(findAllPostsPath)
        .retrieve()
        .body(new ParameterizedTypeReference<>() {});
  }
}
