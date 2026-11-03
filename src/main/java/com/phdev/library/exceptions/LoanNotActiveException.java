package com.phdev.library.exceptions;

public class LoanNotActiveException extends RuntimeException {
    public LoanNotActiveException(String message) {
        super(message);
    }
}
