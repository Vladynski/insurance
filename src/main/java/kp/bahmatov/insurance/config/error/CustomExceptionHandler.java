package kp.bahmatov.insurance.config.error;

import kp.bahmatov.insurance.exceptions.CustomException;
import kp.bahmatov.insurance.exceptions.validation.ValidationErrorData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ValidationErrorData errorData = new ValidationErrorData();
        exception.getFieldErrors().forEach(
                field -> errorData.addItem(field.getField(), field.getDefaultMessage())
        );
        return new ResponseEntity<>(errorData, HttpStatus.BAD_REQUEST);
    }
}
