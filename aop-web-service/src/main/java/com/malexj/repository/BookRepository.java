package com.malexj.repository;

import com.malexj.entity.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

  List<Book> findAllBooksByTitle(String title);
}
