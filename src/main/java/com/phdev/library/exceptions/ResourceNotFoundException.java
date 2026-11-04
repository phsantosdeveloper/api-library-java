package com.phdev.library.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Object id) {
        super("Resource not found, id: " + id);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
