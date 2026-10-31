package com.phdev.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 254, message = "Name must be between 3 and 254 characters long")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 254, message = "Email must have at most 254 characters")
        String email,

        @NotBlank(message = "Phone is required")
        @Pattern(
                regexp = "^[1-9]{2}[0-9]{8,9}$",
                message = "Phone must contain DDD and 10 or 11 digits"
        )
        String phone
) {
}
