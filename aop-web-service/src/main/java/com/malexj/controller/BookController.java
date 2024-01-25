package com.malexj.controller;

import com.malexj.model.request.BookDtoRequest;
import com.malexj.model.response.BookDtoResponse;
import com.malexj.model.response.BooksDtoResponse;
import com.malexj.service.BookService;
import java.util.Objects;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/books", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BookController {

  private final BookService service;
  private final Predicate<BooksDtoResponse> bookTitlePredicate = r -> !r.getBooks().isEmpty();

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BookDtoResponse> addNewBook(@RequestBody BookDtoRequest request) {
    return ResponseEntity.ok(service.addBook(request));
  }

  @GetMapping()
  public ResponseEntity<BooksDtoResponse> findBooks(
      @RequestParam(name = "title", required = false) String title) {
    return Objects.nonNull(title)
        ? findBooksByTitle(title)
        : ResponseEntity.ok(service.findBooks());
  }

  private ResponseEntity<BooksDtoResponse> findBooksByTitle(String title) {
    BooksDtoResponse response = service.findBooksByTitle(title);
    if (response.getBooks().isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(response);
  }
}
