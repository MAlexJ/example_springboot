package com.malex.controller;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cats")
public class RestApiController {

  private final Map<Integer, Cat> cats = new ConcurrentHashMap<>();

  @PostMapping
  public ResponseEntity<Cat> createCat(@RequestBody Cat json, UriComponentsBuilder uriBuilder) {
    var id = json.id;
    cats.put(id, new Cat(json));
    var uri = uriBuilder.path("/cats/{id}").build(id);
    return ResponseEntity.created(uri).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Cat> getCat(@PathVariable Integer id) {
    return Optional.ofNullable(cats.get(id))
        .map(cat -> ResponseEntity.ok().body(cat))
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cat not found by id - " + id));
  }

  @GetMapping
  public ResponseEntity<Cats> retrieveCats() {
    return ResponseEntity.ok(new Cats(cats));
  }

  public record Cat(Integer id, String name, String threadName) {
    public Cat(Cat cat) {
      this(cat.id, cat.name, Thread.currentThread().getName());
    }
  }

  public record Cats(Collection<Cat> cats, int total) {

    public Cats(Map<Integer, Cat> map) {
      this(map.values(), map.values().size());
    }
  }
}
