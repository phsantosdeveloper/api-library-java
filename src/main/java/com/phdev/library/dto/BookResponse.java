package com.phdev.library.dto;

import com.phdev.library.entities.Book;

public record BookResponse(
        Long id,
        String title,
        String isbn,
        Integer publicationYear,
        Integer availableQuantity,
        Long categoryId,
        Long authorId
) {

    public BookResponse(Book book) {
        this(book.getId(), book.getTitle(), book.getIsbn(), book.getPublicationYear(), book.getAvailableQuantity(),
                book.getCategory().getId(), book.getAuthor().getId());
    }
}
