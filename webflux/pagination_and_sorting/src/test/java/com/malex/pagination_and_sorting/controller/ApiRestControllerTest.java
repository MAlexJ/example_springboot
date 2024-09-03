package com.malex.pagination_and_sorting.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.malex.pagination_and_sorting.entity.Film;
import com.malex.pagination_and_sorting.repository.FilmRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
 * Annotation that can be used for a Spring WebFlux test that focuses only on Spring WebFlux components.
 * Using this annotation will disable full auto-configuration and instead apply only configuration relevant
 * to WebFlux tests (i. e. @Controller, @ControllerAdvice, @JsonComponent, Converter/GenericConverter,
 * and WebFluxConfigurer beans but not @Component, @Service or @Repository beans).
 */
@WebFluxTest
class ApiRestControllerTest {

  private static final String USER_URL = "/v1/films";

  private static final List<Film> films =
      List.of(
          new Film(1L, "Film 2"),
          new Film(2L, "Film 3"),
          new Film(3L, "Film 6"),
          new Film(4L, "Film 4"),
          new Film(5L, "Film 11"),
          new Film(6L, "Film 134"),
          new Film(7L, "Film 7"),
          new Film(8L, "Film 6"),
          new Film(9L, "Film 9"),
          new Film(10L, "Film 33"));

  @Autowired private WebTestClient webClient;

  @MockBean private FilmRepository repository;

  @BeforeEach
  public void init() {
    when(repository.findAllBy(any(Pageable.class))).thenReturn(Flux.fromIterable(films));
    when(repository.count()).thenReturn(Mono.just(Integer.toUnsignedLong(films.size())));
  }

  /*
   * Response:
   *
   * { "content": [], "pageable": { "pageNumber": 1, "pageSize": 5, "sort": { "empty": false,
   * "sorted": true, "unsorted": false }, "offset": 5, "unpaged": false, "paged": true }, "last":
   * true, "totalElements": 2, "totalPages": 1, "first": false, "size": 5, "number": 1, "sort": {
   * "empty": false, "sorted": true, "unsorted": false }, "numberOfElements": 0, "empty": true }
   */
  @Test
  void testUsers() {
    webClient
        .get()
        .uri(USER_URL)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.pageable.pageNumber")
        .isEqualTo("0")
        .jsonPath("$.pageable.pageSize")
        .isEqualTo("10")
        .jsonPath("$.totalElements")
        .isEqualTo("10");
  }
}
