package com.phdev.library.dto;

import com.phdev.library.entities.Author;

public record AuthorResponse(
        Long id,
        String name
) {

    public AuthorResponse(Author author) {
        this(author.getId(), author.getName());
    }
}
