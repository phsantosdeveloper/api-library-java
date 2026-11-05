package com.phdev.library.repositories;

import com.phdev.library.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByAuthorId(Long authorId, Pageable pageable);
    Page<Book> findByAuthorNameContainingIgnoreCase(String authorName, Pageable pageable);
    Page<Book> findByCategoryId(Long categoryId, Pageable pageable);
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
