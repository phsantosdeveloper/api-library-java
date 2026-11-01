package com.phdev.library.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BookAvailableQuantityRequest(

        @NotNull(message = "Available quantity is required")
        @Positive(message = "Available quantity cannot be negative or zero")
        Integer quantity
) {
}
