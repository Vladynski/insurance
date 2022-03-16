package kp.bahmatov.insurance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends CustomException{
    public NotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
