package com.phdev.library.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.ISBN;

import java.time.Year;

public record BookRequest(

        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 254, message = "The title must be between 3 and 254 characters long")
        String title,

        @NotBlank(message = "ISBN is required")
        @ISBN(type = ISBN.Type.ANY, message = "Invalid ISBN")
        String isbn,

        @NotNull(message = "Publication year is required")
        @Min(value = 1, message = "Publication year must be greater than zero")
        Integer publicationYear,

        @NotNull(message = "Available quantity is required")
        @PositiveOrZero(message = "Available quantity cannot be negative")
        Integer availableQuantity,

        @NotNull(message = "Category Id is required")
        @Positive(message = "Category Id cannot be negative or zero")
        Long categoryId,

        @NotNull(message = "Author Id is required")
        @Positive(message = "Author Id cannot be negative or zero")
        Long authorId
) {

        @AssertTrue(message = "Publication year cannot be in the future")
        public boolean isPublicationYearValid() {
                return publicationYear == null
                        || publicationYear <= Year.now().getValue();
        }
}
