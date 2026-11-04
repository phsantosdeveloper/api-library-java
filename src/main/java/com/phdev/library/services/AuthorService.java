package com.phdev.library.services;

import com.phdev.library.dto.AuthorRequest;
import com.phdev.library.dto.AuthorResponse;
import com.phdev.library.entities.Author;
import com.phdev.library.repositories.AuthorRepository;
import com.phdev.library.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public Page<AuthorResponse> findAll(Pageable pageable) {
        Page<Author> authors = authorRepository.findAll(pageable);
        return authors.map(author -> new AuthorResponse(author));
    }

    @Transactional(readOnly = true)
    public AuthorResponse findById(Long id) {
        Author author = findByAuthorId(id);
        return new AuthorResponse(author);
    }

    @Transactional(readOnly = true)
    public Page<AuthorResponse> findByName(String name, Pageable pageable) {
        Page<Author> authors = authorRepository.findByNameContainingIgnoreCase(name, pageable);
        return authors.map(author -> new AuthorResponse(author));
    }

    @Transactional
    public AuthorResponse insert(AuthorRequest request) {
        Author author = new Author(null, request.name());
        authorRepository.save(author);
        return new AuthorResponse(author);
    }

    @Transactional
    public AuthorResponse update(Long id, AuthorRequest request) {
        Author author = findByAuthorId(id);
        author.setName(request.name());
        authorRepository.save(author);

        return new AuthorResponse(author);
    }

    @Transactional
    public void delete(Long id) {
        Author author = findByAuthorId(id);
        authorRepository.delete(author);
    }

    private Author findByAuthorId(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return author;
    }

}
