package kp.bahmatov.insurance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends CustomException {
    public UserAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
