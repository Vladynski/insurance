package kp.bahmatov.insurance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class PasswordConfirmationException extends CustomException{
    public PasswordConfirmationException() {
        super("Неверно указан пароль", HttpStatus.FORBIDDEN);
    }
}
