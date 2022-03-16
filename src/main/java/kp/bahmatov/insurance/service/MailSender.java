package kp.bahmatov.insurance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;

@Service
public class MailSender {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.username}")
    private String username;

    public void send(String emailTo, String subject, String message) throws MessagingException {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "UTF-8");
        mailMessage.setContent(message.replaceAll("\n", "<br>"), "text/html; charset=UTF-8");
        helper.setFrom(username);
        helper.setTo(emailTo);
        helper.setSubject(subject);

        mailSender.send(mailMessage);
    }
}
