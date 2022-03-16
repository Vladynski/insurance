package kp.bahmatov.insurance.service;

import kp.bahmatov.insurance.domain.dto.user.CreateUserDto;
import kp.bahmatov.insurance.domain.structure.InsuranceData;
import kp.bahmatov.insurance.domain.structure.Role;
import kp.bahmatov.insurance.domain.structure.User;
import kp.bahmatov.insurance.exceptions.SendMailException;
import kp.bahmatov.insurance.exceptions.UserAlreadyExistsException;
import kp.bahmatov.insurance.exceptions.UserNotFoundException;
import kp.bahmatov.insurance.repo.InsuranceDataRepo;
import kp.bahmatov.insurance.repo.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final InsuranceDataRepo insuranceDataRepo;
    private final PasswordEncoder passwordEncoder;
    private final MailSender mailSender;
    @Value("${host}")
    private String host;

    public UserService(UserRepo userRepo, InsuranceDataRepo insuranceDataRepo, PasswordEncoder passwordEncoder, MailSender mailSender) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.insuranceDataRepo = insuranceDataRepo;
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    public void addUser(CreateUserDto createUserDto) {
        if (userRepo.findByEmail(createUserDto.getEmail()).orElse(null) != null)
            throw new UserAlreadyExistsException("A user is already registered to this email");

        User user = new User();

        user.setFirstName(createUserDto.getFirstName());
        user.setSecondName(createUserDto.getSecondName());
        user.setPatronymic(createUserDto.getPatronymic());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setRegistrationDate(LocalDateTime.now());
        user.setRoles(Set.of(Role.USER));
        user.setInsurance(new InsuranceData(user));

        sendActivateEmail(user);

        userRepo.save(user);
    }

    private void sendActivateEmail(User user) {
        String message = String.format("""
                Здравствуйте %s %s %s
                Добро пожаловать в Insurance
                Чтобы подтвердить почту перейдите по ссылке <a href="%s/registration/activate?code=%s"><u>клик</u></a>
                """, user.getSecondName(), user.getFirstName(), user.getPatronymic(), host, user.getActivationCode());

        try {
            mailSender.send(user.getEmail(), "Активация аккаунта", message);
        } catch (MailSendException e) {
            throw new SendMailException();
        } catch (MessagingException e) {
            //fixme add log
            throw new RuntimeException(e);
        }
    }

    public void activateUser(String code) {
        User user = userRepo.findByActivationCode(code).orElseThrow(() -> new UserNotFoundException("Activation code is invalid"));
        user.setActivationCode(null);
        userRepo.save(user);
    }
}
