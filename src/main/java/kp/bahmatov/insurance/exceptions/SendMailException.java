package kp.bahmatov.insurance.exceptions;

import org.springframework.http.HttpStatus;

public class SendMailException extends CustomException{
    public SendMailException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
