package com.phdev.library.entities;

import com.phdev.library.entities.enums.LoanStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(nullable = false)
    private long version;

    @Column(nullable = false)
    private LocalDateTime loanDate;

    @Column(nullable = false)
    private LocalDate returnExpectedDate;

    private LocalDateTime returnDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public Loan() {
    }

    public Loan(Long id, LocalDateTime loanDate, LocalDate returnExpectedDate, LocalDateTime returnDate, LoanStatus status, Book book, User user) {
        this.id = id;
        this.loanDate = loanDate;
        this.returnExpectedDate = returnExpectedDate;
        this.returnDate = returnDate;
        this.status = status;
        this.book = book;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnExpectedDate() {
        return returnExpectedDate;
    }

    public void setReturnExpectedDate(LocalDate returnExpectedDate) {
        this.returnExpectedDate = returnExpectedDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
