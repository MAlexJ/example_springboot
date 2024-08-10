package com.malex.pagination_and_sorting_webflux.controller;

import com.malex.pagination_and_sorting_webflux.entity.Film;
import com.malex.pagination_and_sorting_webflux.repository.FilmRepository;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;


/**
 * WebFlux supports using a single value reactive type to produce the ResponseEntity asynchronously,
 * and/or single and multi-value reactive types for the body. This allows a variety of async
 * responses with ResponseEntity as follows:
 *
 * <ul>
 *   <li>ResponseEntity<Mono<T>> or ResponseEntity<Flux<T>> make the response status and headers
 *       known immediately while the body is provided asynchronously at a later point. Use Mono if
 *       the body consists of 0..1 values or Flux if it can produce multiple values.
 *   <li>Mono<ResponseEntity<T>> provides all three: response status, headers, and body,
 *       asynchronously at a later point. This allows the response status and headers to vary
 *       depending on the outcome of asynchronous request handling.
 *   <li>Mono<ResponseEntity<Mono<T>>> or Mono<ResponseEntity<Flux<T>>> are yet another possible,
 *       albeit less common alternative. They provide the response status and headers asynchronously
 *       first and then the response body, also asynchronously, second.
 * </ul>
 *
 * Link to info: <a
 * href="https://docs.spring.io/spring-framework/reference/web/webflux/controller/ann-methods/responseentity.html">ResponseEntity</a>
 */
@RequestMapping("/v1/films")
@RestController
@RequiredArgsConstructor
public class ApiRestController {

  private final FilmRepository repository;

  @GetMapping
  public Mono<Page<Film>> findAllProducts( @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id") String sort) {
    var pageable = PageRequest.of(page, size, Sort.by(sort));
    return repository
        .findAllBy(pageable)
        .collectList()
        .zipWith(repository.count())
        .map(buildPageResponse(pageable));
  }


  @PostMapping
  public ResponseEntity<Mono<Film>> save(@RequestBody Film film) {
    return ResponseEntity.ok().body(repository.save(film));
  }

  private Function<Tuple2<List<Film>, Long>, Page<Film>> buildPageResponse(Pageable pageable) {
    return tuple2 -> {
      var filmList = tuple2.getT1();
      var size = tuple2.getT2();
      return new PageImpl<>(filmList, pageable, size);
    };
  }
}
