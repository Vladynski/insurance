package kp.bahmatov.insurance.exceptions;

import org.springframework.http.HttpStatus;

public class SendMailException extends CustomException{
    public SendMailException() {
        super("Не удалось отправить письмо с кодом активации аккаунта на почту. Проверьте правильность введённых данных", HttpStatus.BAD_REQUEST);
    }
}
