package com.phdev.library.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record LoanUpdate(

        @NotNull(message = "Return expected date is required")
        @Future(message = "Return expected date must be after today")
        LocalDate returnExpectedDate
) {
}
