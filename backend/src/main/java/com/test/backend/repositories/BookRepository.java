package com.test.backend.repositories;

import com.test.backend.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByTitleContainingOrDescriptionContaining(String title, String description);
}
