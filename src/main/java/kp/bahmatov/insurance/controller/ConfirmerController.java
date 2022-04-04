package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.service.ConfirmerService;
import kp.bahmatov.insurance.service.MailSender;
import kp.bahmatov.insurance.service.SettingsService;
import kp.bahmatov.insurance.service.interfaces.Auth;
import kp.bahmatov.insurance.util.LetterFormatter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private void createConfirmer() {
        if (confirmerService.canIGenerateNewCode()) {
            String code = confirmerService.generateNewCodeForMe(
                    SettingsService.getConfirmerLifetimeMinutes().getIntValue(),
                    SettingsService.getConfirmerLifetimeHours().getIntValue());
            try {
                mailSender.send(auth.getUser().getEmail(), "Код подтверждения", LetterFormatter.getLetterWithCode(code));
            } catch (MessagingException e) {
                //FIXME
                e.printStackTrace();
            }
        }
    }
}
