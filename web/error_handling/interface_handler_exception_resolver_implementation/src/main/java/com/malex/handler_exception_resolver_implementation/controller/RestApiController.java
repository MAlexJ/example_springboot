package com.malex.handler_exception_resolver_implementation.controller;

import java.net.URI;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1")
public class RestApiController {

  private final RestTemplate restTemplate;

  @Value("${webclient.url}")
  private String url;

  @Value("${webclient.path}")
  private String path;

  public RestApiController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @GetMapping("/products/{productId}")
  public ResponseEntity<String> processRequest(@PathVariable Integer productId) {

    // NullPointerException
    Objects.requireNonNull(productId, "productId cannot be null");

    // Not Found: 404 Http status code whit 5555 product id
    var uri = buildUri(productId);
    return restTemplate.getForEntity(uri, String.class);
  }

  /*
   * Guide to UriComponentsBuilder in Spring
   *
   * The builder works in conjunction with the UriComponents class – an immutable container for URI components.
   *
   * A new UriComponentsBuilder class helps to create UriComponents instances by providing fine-grained control
   * over all aspects of preparing a URI including construction, expansion from template variables, and encoding.
   * link: https://www.baeldung.com/spring-uricomponentsbuilder#introduction
   */
  private URI buildUri(Integer productId) {
    return UriComponentsBuilder.fromUriString(url) //
        .path(path)
        .path("/{productId}")
        .build(productId);
  }
}
