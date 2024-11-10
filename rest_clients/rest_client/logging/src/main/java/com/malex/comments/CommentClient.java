package com.malex.comments;

import java.util.List;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/*
   API : https://jsonplaceholder.typicode.com
*
   Resources: JSONPlaceholder comes with a set of 6 common resources:
*
   /posts	100 posts
   /comments	500 comments
   /albums	100 albums
   /photos	5000 photos
   /todos	200 todos
   /users	10 users
*
*
    Routes: All HTTP methods are supported.
*
    GET	/posts
    GET	/posts/1
    GET	/posts/1/comments
    GET	/comments?postId=1
    POST	/posts
    PUT	/posts/1
    PATCH	/posts/1
    DELETE	/posts/1
*/
@Slf4j
@Service
public class CommentClient {

  @Value("${webservice.client.path.comments.findAll}")
  private String findAllCommentsPath;

  private final RestClient restClient;

  public CommentClient(
      @Value("${webservice.client.url}") String baseUrl, RestClient.Builder builder) {
    this.restClient =
        builder
            .baseUrl(baseUrl)
            .requestInterceptor(applyHttpRequestResponseLoginInterceptor())
            .build();
  }

  private ClientHttpRequestInterceptor applyHttpRequestResponseLoginInterceptor() {
    return (request, body, execution) -> {
      logRequest(request);
      ClientHttpResponse response = execution.execute(request, body);
      logResponse(response);
      return response;
    };
  }

  private static void logRequest(HttpRequest request) {
    var method = request.getMethod();
    var uri = request.getURI();
    var headers = request.getHeaders();
    // request body !!!???
    log.info("Request URI: - {}: - {}", method, uri);
    log.info("Request headers: - {}", headers);
  }

  @SneakyThrows
  private void logResponse(ClientHttpResponse response) {
    HttpStatusCode statusCode = response.getStatusCode();
    log.info("Response status code: - {}", statusCode);

    HttpHeaders headers = response.getHeaders();
    log.info("Response headers: - {}", headers);

    // no response in http call!!
    //    InputStream body = response.getBody();
    //    log.info("Response body: - {}", new String(body.readAllBytes(), StandardCharsets.UTF_8));
  }

  public List<Comment> findAllComments() {
    return restClient
        .get()
        .uri(findAllCommentsPath)
        .retrieve()
        .body(new ParameterizedTypeReference<List<Comment>>() {});
  }
}
