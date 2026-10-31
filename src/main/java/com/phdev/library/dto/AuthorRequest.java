package com.phdev.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthorRequest(

        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 254, message = "Name must be between 3 and 254 characters long")
        String name
) {
}
