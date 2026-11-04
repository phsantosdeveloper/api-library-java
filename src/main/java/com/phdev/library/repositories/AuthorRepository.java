package com.phdev.library.repositories;

import com.phdev.library.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Page<Author> findByNameContainingIgnoreCase(Pageable pageable, String name);
}
