package com.malex.interceptor.webservice.post;

import com.malex.interceptor.controller.post.Post;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PostRestClient {

  private final RestClient restClient;

  @Value("${webservice.client.path.posts.findAll}")
  private String findAllPostsPath;

  public PostRestClient(@Value("${webservice.client.url}") String baseUrl, RestClient.Builder builder) {
    this.restClient = builder.baseUrl(baseUrl).build();
  }

  public List<Post> findAll() {
    return restClient
        .get()
        .uri(findAllPostsPath)
        .retrieve()
        .body(new ParameterizedTypeReference<>() {});
  }
}
