package com.malex.interceptor.comment;

import com.malex.interceptor.comment.v1.CommentV1;
import com.malex.interceptor.comment.v2.CommentV2;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CommentClient {

  private final Logger logger = Logger.getLogger(CommentClient.class.getName());

  private final RestClient restClient;

  @Value("${webservice.client.path.comments.findAll}")
  private String findAllCommentsPath;

  public CommentClient(
      @Value("${webservice.client.url}") String baseUrl,
      @Value("${webservice.connectTimeout:1}") Long connectTimeout,
      @Value("${webservice.readTimeout:2}") Long readTimeout,
      RestClient.Builder builder,
      CommentHttpInterceptor interceptor) {
    this.restClient =
        builder
            .baseUrl(baseUrl)
            .requestFactory(applyHttpRequestFactory(connectTimeout, readTimeout))
            .requestInterceptor(applyCustomClientHttpRequestInterceptor())
            .requestInterceptor(interceptor)
            .build();
  }

  private SimpleClientHttpRequestFactory applyHttpRequestFactory(
      Long connectTimeout, Long readTimeout) {
    var requestFactorySettings =
        ClientHttpRequestFactorySettings.DEFAULTS
            .withConnectTimeout(Duration.ofSeconds(connectTimeout))
            .withReadTimeout(Duration.ofSeconds(readTimeout));

    return ClientHttpRequestFactories.get(
        SimpleClientHttpRequestFactory.class, requestFactorySettings);
  }

  private ClientHttpRequestInterceptor applyCustomClientHttpRequestInterceptor() {
    return (request, body, execution) -> {
      logger.info(" > Request method: " + request.getMethod());
      logger.info(" > Request uri: " + request.getURI());
      logger.info(" > Request headers: " + request.getHeaders());
      ClientHttpResponse clientHttpResponse = execution.execute(request, body);
      logger.info(" > Response status:" + clientHttpResponse.getStatusText());
      return clientHttpResponse;
    };
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
