package com.phdev.library.services;

import com.phdev.library.dto.LoanRequest;
import com.phdev.library.dto.LoanResponse;
import com.phdev.library.dto.LoanUpdate;
import com.phdev.library.entities.Book;
import com.phdev.library.entities.Loan;
import com.phdev.library.entities.User;
import com.phdev.library.entities.enums.LoanStatus;
import com.phdev.library.repositories.BookRepository;
import com.phdev.library.repositories.LoanRepository;
import com.phdev.library.repositories.UserRepository;
import com.phdev.library.services.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public Page<LoanResponse> findAll(Pageable pageable) {
        Page<Loan> loans = loanRepository.findAll(pageable);
        return loans.map(loan -> new LoanResponse(loan));
    }

    @Transactional(readOnly = true)
    public LoanResponse findById(Long id) {
        Loan loan = findByLoanId(id);
        return new LoanResponse(loan);
    }

    @Transactional
    public LoanResponse insert(LoanRequest request) {
        LocalDateTime loanDate = LocalDateTime.now();
        LocalDate returnExpectedDate = loanDate.toLocalDate().plusDays(30);

        User user = findByUserId(request.UserId());
        Book book = findByBookId(request.BookId());

        validateAvailableQuantityOfBooks(book);

        Loan loan = new Loan(null, loanDate, returnExpectedDate, null, LoanStatus.ACTIVE, book, user);
        removeBookFromStock(loan.getBook());

        loanRepository.save(loan);
        return new LoanResponse(loan);
    }

    @Transactional
    public LoanResponse updateReturnExpectedDate(Long id, LoanUpdate update) {
        Loan loan = findByLoanId(id);

        validateReturnandLoanStatus(loan);

        loan.setReturnExpectedDate(update.returnExpectedDate());
        loanRepository.save(loan);
        return new LoanResponse(loan);
    }

    @Transactional
    public LoanResponse confirmReturn(Long id) {
        Loan loan = findByLoanId(id);

        validateReturnandLoanStatus(loan);

        loan.setReturnDate(LocalDateTime.now());
        loan.setStatus(LoanStatus.RETURNED);
        returnBookToStock(loan.getBook());
        loanRepository.save(loan);

        return new LoanResponse(loan);
    }

    @Transactional
    public LoanResponse cancel(Long id) {
        Loan loan = findByLoanId(id);

        validateReturnandLoanStatus(loan);

        loan.setStatus(LoanStatus.CANCELLED);
        returnBookToStock(loan.getBook());
        loanRepository.save(loan);

        return new LoanResponse(loan);
    }

    private Loan findByLoanId(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return loan;
    }

    private User findByUserId(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return user;
    }

    private Book findByBookId(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return book;
    }

    private void validateReturnandLoanStatus(Loan loan) {
        if(loan.getReturnDate() != null || loan.getStatus().equals(LoanStatus.RETURNED)
                || loan.getStatus().equals(LoanStatus.CANCELLED)) {
            throw new RuntimeException();
        }
    }

    private void validateAvailableQuantityOfBooks(Book book) {
        if(book.getAvailableQuantity() <= 0) {
            throw new RuntimeException();
        }
    }

    private void returnBookToStock(Book book) {
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
    }

    private void removeBookFromStock(Book book) {
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
    }

}
