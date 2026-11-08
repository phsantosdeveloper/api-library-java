package com.phdev.library.handler;

import com.phdev.library.exceptions.BookUnavailableException;
import com.phdev.library.exceptions.InvalidNumberBooksWithdrawException;
import com.phdev.library.exceptions.LoanNotActiveException;
import com.phdev.library.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<StandardError> optimisticLocking(OptimisticLockingFailureException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        String error = "Concurrent update";
        String message = "The record was changed by another operation. Reload and try again.";

        StandardError err = new StandardError(Instant.now(), status.value(), error, message, request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(BookUnavailableException.class)
    public ResponseEntity<StandardError> bookUnavailable(BookUnavailableException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Book unavailable";

        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataBaseIntegrityViolation(DataIntegrityViolationException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        String error = "Database conflict";
        String message = "The operation violates a database integrity constraint";

        StandardError err = new StandardError(Instant.now(), status.value(), error, message, request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidNumberBooksWithdrawException.class)
    public ResponseEntity<StandardError> invalidNumberBooksWithdraw(InvalidNumberBooksWithdrawException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Invalid withdrawal amount";

        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(LoanNotActiveException.class)
    public ResponseEntity<StandardError> loanNotActive(LoanNotActiveException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = "Loan not active";

        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        String error = "Entity validation error";
        String message = "Error in the validation of data for inserting or updating a record";

        ValidationError err = new ValidationError(Instant.now(), status.value(), error, message, request.getRequestURI());

        for(FieldError f : e.getBindingResult().getFieldErrors()) {
            err.addFieldMessage(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        String error = "Resource not found";

        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}
