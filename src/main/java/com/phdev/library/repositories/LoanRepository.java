package com.phdev.library.repositories;

import com.phdev.library.entities.Loan;
import com.phdev.library.entities.enums.LoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    Page<Loan> findByUserId(Long id, Pageable pageable);
    Page<Loan> findByBookId(Long id, Pageable pageable);
    Page<Loan> findByReturnExpectedDateIsBeforeAndStatus(LocalDate currentDate, LoanStatus status, Pageable pageable);
}
