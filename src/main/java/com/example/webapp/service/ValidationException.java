package com.example.webapp.service;

import java.util.Map;

public class ValidationException extends RuntimeException {
    private final Map<String, String> errors;

    public ValidationException(String field, String message) {
        this(Map.of(field, message));
    }

    public ValidationException(Map<String, String> errors) {
        super(errors.values().iterator().next());
        this.errors = Map.copyOf(errors);
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
