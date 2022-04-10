package kp.bahmatov.insurance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class SendRequestTimeoutException extends CustomException {
    public SendRequestTimeoutException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
