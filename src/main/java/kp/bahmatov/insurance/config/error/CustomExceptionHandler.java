package kp.bahmatov.insurance.config.error;

import kp.bahmatov.insurance.exceptions.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({CustomException.class})
    public ResponseEntity<ErrorModel> customExceptionHandler(CustomException exception) {
        return new ResponseEntity<>(
                new ErrorModel(exception.getMessage()),
                exception.getStatus()
        );
    }
}
