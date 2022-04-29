package kp.bahmatov.insurance.exceptions.validation;

import lombok.Getter;

public class CustomValidationException extends RuntimeException {
    @Getter
    private final String fieldName;

    public CustomValidationException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }
}
