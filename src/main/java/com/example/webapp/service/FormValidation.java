package com.example.webapp.service;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.LinkedHashMap;

public final class FormValidation {
    private static final Validator VALIDATOR =
            Validation.buildDefaultValidatorFactory().getValidator();

    private FormValidation() {}

    public static void validate(Object dto) {
        var errors = new LinkedHashMap<String, String>();
        VALIDATOR
                .validate(dto)
                .forEach(v -> errors.putIfAbsent(v.getPropertyPath().toString(), v.getMessage()));
        if (!errors.isEmpty()) throw new ValidationException(errors);
    }
}
