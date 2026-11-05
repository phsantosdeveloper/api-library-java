package com.phdev.library.repositories;

import com.phdev.library.entities.Book;
import com.phdev.library.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByAuthorId(Long authorId, Pageable pageable);
    Page<Book> findByAuthorNameContainingIgnoreCase(String authorName, Pageable pageable);
    Page<Book> findByCategoryId(Long categoryId, Pageable pageable);
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Optional<Book> findByIsbn(String isbn);

    @Query("SELECT obj FROM Book obj WHERE obj.availableQuantity > 0")
    Page<Book> findAvailableBooks(Pageable pageable);
}
