package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.exceptions.SendMailException;
import kp.bahmatov.insurance.service.ConfirmerService;
import kp.bahmatov.insurance.service.MailSender;
import kp.bahmatov.insurance.service.interfaces.Auth;
import kp.bahmatov.insurance.util.LetterFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("confirm")
public class ConfirmerController {
    private final ConfirmerService confirmerService;
    private final Auth auth;
    private final MailSender mailSender;

    public ConfirmerController(ConfirmerService confirmerService,
                               Auth auth,
                               MailSender mailSender) {
        this.confirmerService = confirmerService;
        this.auth = auth;
        this.mailSender = mailSender;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    private void createConfirmer() {
        String code;
        try {
            if (confirmerService.canIGenerateNewCode())
                code = confirmerService.generateNewCodeForMe();
            else
                code = confirmerService.getMyConfirmer().getCode();
            if (!mailSender.send(auth.getUser().getEmail(), "Код подтверждения", LetterFormatter.getLetterWithCode(code))) {
                throw new SendMailException("Не удалось отправить письмо с кодом, попробуйте повторить позже");
            }
        } catch (MessagingException e) {
            throw new SendMailException("Внутренняя ошибка сервера: не удалось отправить письмо с кодом");
        }
    }
}
