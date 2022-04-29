package kp.bahmatov.insurance.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class MailSender {
    private final HashMap<String, LocalDateTime> sendTimeToUser = new HashMap<>();

    private final JavaMailSender mailSender;

    @Value("${mail.username}")
    private String username;

    public MailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean send(String emailTo, String subject, String message) throws MessagingException {
        return send(emailTo, subject, message, false);
    }

    public boolean send(String emailTo, String subject, String message, boolean outOfTurn) throws MessagingException {
        LocalDateTime now = LocalDateTime.now();
        if (outOfTurn || sendTimeToUser.getOrDefault(emailTo, LocalDateTime.MIN).isBefore(now)) {
            MimeMessage mailMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "UTF-8");
            mailMessage.setContent(message.replaceAll("\n", "<br>"), "text/html; charset=UTF-8");
            helper.setFrom(username);
            helper.setTo(emailTo);
            helper.setSubject(subject);

            sendTimeToUser.put(emailTo, now.plusSeconds(SettingsService.getMailSendTimeoutSeconds().getIntValue()));
            mailSender.send(mailMessage);
            return true;
        }
        return false;
    }
}
