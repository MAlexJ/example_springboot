package com.malex.interceptor.post.webclient;

import com.malex.interceptor.post.Post;
import com.malex.interceptor.post.webclient.interceptor.SimpleReplayHttpInterceptor;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TestPostClient {

  private static final String BASE_URL = "http://localhost:8080";

  private static final String TEST_API_PATH = "/api/posts/internal";

  private final RestClient restClient;

  public TestPostClient(RestClient.Builder builder, SimpleReplayHttpInterceptor interceptor) {
    this.restClient = builder.baseUrl(BASE_URL).requestInterceptor(interceptor).build();
  }

  public List<Post> testApi() {
    return restClient
        .get()
        .uri(TEST_API_PATH)
        .retrieve()
        .body(new ParameterizedTypeReference<>() {});
  }
}
