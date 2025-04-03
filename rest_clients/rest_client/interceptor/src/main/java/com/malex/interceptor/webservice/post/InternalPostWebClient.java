package com.malex.interceptor.webservice.post;

import com.malex.interceptor.controller.post.Post;
import com.malex.interceptor.webservice.post.interceptor.SimpleRetryHttpInterceptor;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class InternalPostWebClient {

  @Value("${webservice.internal.path.posts.findAll}")
  private String findAllPostsPath;

  private final RestClient restClient;

  public InternalPostWebClient(
      @Value("${webservice.internal.url}") String baseUrl,
      RestClient.Builder builder,
      SimpleRetryHttpInterceptor interceptor) {
    this.restClient = builder.baseUrl(baseUrl).requestInterceptor(interceptor).build();
  }

  public List<Post> testApi() {
    return restClient
        .get()
        .uri(findAllPostsPath)
        .retrieve()
        .body(new ParameterizedTypeReference<>() {});
  }
}
