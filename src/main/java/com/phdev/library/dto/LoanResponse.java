package com.phdev.library.dto;

import com.phdev.library.entities.Loan;
import com.phdev.library.entities.enums.LoanStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record LoanResponse(
        Long id,
        LocalDateTime loanDate,
        LocalDate returnExpectedDate,
        LocalDateTime returnDate,
        LoanStatus status,
        BookSummary book,
        UserSummary user
) {

    public LoanResponse(Loan loan) {
        this(loan.getId(), loan.getLoanDate(), loan.getReturnExpectedDate(), loan.getReturnDate(), loan.getStatus(),
                new BookSummary(loan.getBook()),
                new UserSummary(loan.getUser()));
    }
}
