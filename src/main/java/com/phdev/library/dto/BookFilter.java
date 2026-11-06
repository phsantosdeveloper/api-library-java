package com.phdev.library.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record BookFilter(
        @Size(min = 1, message = "Title must not be blank")
        String title,

        @Positive(message = "Author Id must be positive")
        Long authorId,

        @Size(min = 1, message = "Author name must not be blank")
        String authorName,

        @Positive(message = "Category Id must be positive")
        Long categoryId,

        @Size(min = 1, message = "ISBN must not be blank")
        String isbn
) {
    public BookFilter {
        title = strip(title);
        authorName = strip(authorName);
        isbn = strip(isbn);
    }

    private static String strip(String value) {
        return value == null ? null : value.strip();
    }
}
