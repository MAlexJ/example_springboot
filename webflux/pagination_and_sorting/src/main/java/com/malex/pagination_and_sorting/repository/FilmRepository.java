package com.malex.pagination_and_sorting.repository;

import com.malex.pagination_and_sorting.entity.Film;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface FilmRepository extends ReactiveCrudRepository<Film, Long> {

  Flux<Film> findAllBy(Pageable pageable);
}
