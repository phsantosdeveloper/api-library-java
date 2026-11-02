package com.phdev.library.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LoanRequest(

        @NotNull(message = "Book Id is required")
        @Positive(message = "Book ID must be positive")
        Long bookId,

        @NotNull(message = "User Id is required")
        @Positive(message = "User ID must be positive")
        Long userId
) {
}
