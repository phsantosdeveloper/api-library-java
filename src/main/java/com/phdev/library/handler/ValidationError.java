package com.phdev.library.handler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Instant moment, Integer status, String error, String message, String path) {
        super(moment, status, error, message, path);
    }

    public List<FieldMessage> getFieldMessages() {
        return errors;
    }

    public void addFieldMessage(String field, String message) {
        errors.add(new FieldMessage(field, message));
    }
}
