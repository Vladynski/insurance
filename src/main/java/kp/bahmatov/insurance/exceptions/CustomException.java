package kp.bahmatov.insurance.exceptions;


import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {
    @Getter
    private final HttpStatus status;

    public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.status = httpStatus;
    }
}
