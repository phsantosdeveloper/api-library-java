package com.phdev.library.dto;

public record LoanRequest(
        Long BookId,
        Long UserId
) {
}
