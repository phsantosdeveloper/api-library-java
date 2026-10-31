package com.phdev.library.dto;

import com.phdev.library.entities.Book;

public record BookSummary(
        Long id,
        String title
) {

    public BookSummary (Book book){
        this(book.getId(), book.getTitle());
    }
}
