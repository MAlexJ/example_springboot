package com.malex.interceptor.webservice.comment;

import com.malex.interceptor.controller.comment.v1.CommentV1;
import com.malex.interceptor.controller.comment.v2.CommentV2;
import com.malex.interceptor.webservice.comment.interceptor.FirstInterceptor;
import com.malex.interceptor.webservice.comment.interceptor.SecondInterceptor;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CommentRestClient {

  private final RestClient restClient;

  @Value("${webservice.client.path.comments.findAll}")
  private String findAllCommentsPath;

  public CommentRestClient(
      @Value("${webservice.client.url}") String baseUrl,
      @Value("${webservice.connectTimeout:1}") Long connectTimeout,
      @Value("${webservice.readTimeout:2}") Long readTimeout,
      RestClient.Builder builder,
      FirstInterceptor firstInterceptor,
      SecondInterceptor second) {
    this.restClient =
        builder
            .baseUrl(baseUrl)
            .requestFactory(applyHttpRequestFactory(connectTimeout, readTimeout))
            .requestInterceptor(firstInterceptor)
            .requestInterceptor(second)
            .build();
  }

  private SimpleClientHttpRequestFactory applyHttpRequestFactory(
      Long connectTimeout, Long readTimeout) {
    /*
     * Settings that can be applied when creating a ClientHttpRequestFactory.
     * Since: 3.4.0
     */
    var clientHttpRequestFactorySettings =
        org.springframework.boot.http.client.ClientHttpRequestFactorySettings.defaults()
            .withReadTimeout(Duration.ofSeconds(readTimeout))
            .withConnectTimeout(Duration.ofSeconds(connectTimeout));

    /*
     * Builders for Apache HTTP Components, Jetty, Reactor, JDK and simple client can be obtained using
     * the factory methods on this interface.
     * Since: 3.4.0
     */
    return ClientHttpRequestFactoryBuilder.simple().build(clientHttpRequestFactorySettings);
  }

  public List<CommentV1> findAllV1() {
    return restClient
        .get()
        .uri(findAllCommentsPath)
        .retrieve()
        .body(new ParameterizedTypeReference<>() {});
  }

  public CommentV2 findAllV2() {
    var commentV1s =
        restClient
            .get()
            .uri(findAllCommentsPath)
            .retrieve()
            .body(new ParameterizedTypeReference<List<CommentV1>>() {});

    var limitListOfCommentV1 =
        Optional.ofNullable(commentV1s)
            .map(comments -> comments.stream().limit(10).toList())
            .orElse(Collections.emptyList());

    var totalElements = Optional.ofNullable(commentV1s).map(List::size).orElse(0);

    return new CommentV2(limitListOfCommentV1, totalElements, limitListOfCommentV1.size());
  }
}
