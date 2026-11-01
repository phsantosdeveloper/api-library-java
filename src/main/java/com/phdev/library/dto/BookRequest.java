package com.phdev.library.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.ISBN;

public record BookRequest(

        @NotBlank(message = "Title is required")
        @Size(message = "The title must be between 3 and 254 characters long")
        String title,

        @NotBlank(message = "ISBN is required")
        @ISBN(type = ISBN.Type.ANY, message = "Invalid ISBN")
        String isbn,

        @NotNull(message = "Publication year is required")
        Integer publicationYear,

        @NotNull(message = "Available quantity is required")
        @Positive(message = "Available quantity cannot be negative or zero")
        Integer availableQuantity,

        @NotNull(message = "Category Id is required")
        @Positive(message = "Category Id cannot be negative or zero")
        Long categoryId,

        @NotNull(message = "Author Id is required")
        @Positive(message = "Author Id cannot be negative or zero")
        Long authorId
) {
}
