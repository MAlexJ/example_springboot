package com.malexj.service;

import com.malexj.entity.Book;
import com.malexj.mapper.ObjectMapper;
import com.malexj.model.request.BookDtoRequest;
import com.malexj.model.response.BooksDtoResponse;
import com.malexj.model.response.BookDtoResponse;
import com.malexj.repository.BookRepository;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository repository;

  private final ObjectMapper mapper;

  public BookDtoResponse addBook(BookDtoRequest request) {
    return applyMapping(
        mapper.requestToEntity(request),
        book -> applyMapping(repository.save(book), mapper::entityToResponse));
  }

  public BooksDtoResponse findBooks() {
    return applyMapping(repository.findAll(), BooksDtoResponse::new);
  }

  public BooksDtoResponse findBooksByTitle(String title) {
    return applyMapping(repository.findAllBooksByTitle(title), BooksDtoResponse::new);
  }

  private BookDtoResponse applyMapping(Book book, Function<Book, BookDtoResponse> function) {
    return function.apply(book);
  }

  private BooksDtoResponse applyMapping(
      List<Book> books, Function<List<BookDtoResponse>, BooksDtoResponse> function) {
    return function.apply(books.stream().map(mapper::entityToResponse).toList());
  }
}
