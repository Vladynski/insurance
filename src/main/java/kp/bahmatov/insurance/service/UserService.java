package kp.bahmatov.insurance.service;

import kp.bahmatov.insurance.domain.dto.edit.EditUserDataDto;
import kp.bahmatov.insurance.domain.dto.user.CreateUserInDto;
import kp.bahmatov.insurance.domain.structure.insurance.content.Content;
import kp.bahmatov.insurance.domain.structure.insurance.content.ContentType;
import kp.bahmatov.insurance.domain.structure.insurance.userdata.InsuranceUserData;
import kp.bahmatov.insurance.domain.structure.Role;
import kp.bahmatov.insurance.domain.structure.User;
import kp.bahmatov.insurance.domain.structure.insurance.userdata.InsuranceUserDataStatus;
import kp.bahmatov.insurance.exceptions.PasswordConfirmationException;
import kp.bahmatov.insurance.exceptions.UserAlreadyExistsException;
import kp.bahmatov.insurance.exceptions.UserNotFoundException;
import kp.bahmatov.insurance.repo.InsuranceDataRepo;
import kp.bahmatov.insurance.repo.UserRepo;
import kp.bahmatov.insurance.service.interfaces.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Set;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final InsuranceDataRepo insuranceDataRepo;
    private final PasswordEncoder passwordEncoder;
    private final MailSender mailSender;
    private final Auth auth;
    @Value("${host}")
    private String host;

    public UserService(UserRepo userRepo,
                       InsuranceDataRepo insuranceDataRepo,
                       PasswordEncoder passwordEncoder,
                       MailSender mailSender,
                       Auth auth) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.insuranceDataRepo = insuranceDataRepo;
        this.auth = auth;
        initAdminIfDbEmpty();
    }

    public void throwExceptionIfItIsNotMyPassword(String password) {
        if (password == null || !passwordEncoder.matches(decodePassword(password), auth.getUser().getPassword()))
            throw new PasswordConfirmationException();
    }

    private String decodePassword(String password) {
        return new String(Base64.getDecoder().decode(password));
    }

    @Deprecated
    private void initAdminIfDbEmpty() {
        if (userRepo.count() == 0) {
            User user = new User();
            InsuranceUserData insurance = new InsuranceUserData(user);
            insurance.setPassportId("1234");
            insurance.setPhone("1234");
            Content photo = new Content();
            photo.setType(ContentType.IMAGE_PNG);
            photo.setContent(new byte[0]);
            insurance.setPhoto(photo);
            insurance.setStatus(InsuranceUserDataStatus.CONFIRMED);
            user.setInsuranceData(insurance);
            user.setRoles(Set.of(Role.USER, Role.ADMIN));
            user.setEmail("admin@admin.admin");
            user.setFirstName("admin");
            user.setSecondName("admin");
            user.setPatronymic("admin");
            user.setRegistrationDate(LocalDateTime.now());
            user.setPassword(passwordEncoder.encode("admin"));
            userRepo.save(user);
        }
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    public void addUser(CreateUserInDto createUserDto) {
        if (userRepo.findByEmail(createUserDto.getEmail()).orElse(null) != null)
            throw new UserAlreadyExistsException("A user is already registered to this email");

        User user = new User();

        user.setFirstName(createUserDto.getFirstName());
        user.setSecondName(createUserDto.getSecondName());
        user.setPatronymic(createUserDto.getPatronymic());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
//        user.setActivationCode(UUID.randomUUID().toString()); fixme
        user.setActivationCode(null);
        user.setRegistrationDate(LocalDateTime.now());
        user.setRoles(Set.of(Role.USER));
        user.setInsuranceData(new InsuranceUserData(user));

        sendActivateEmail(user);

        userRepo.save(user);
    }

    private void sendActivateEmail(User user) {
        String message = String.format("""
                Здравствуйте %s %s %s
                Добро пожаловать в CarInsurance
                Чтобы подтвердить почту перейдите по ссылке <a target="_blank" href="%s/registration/activate?code=%s"><u>клик</u></a>
                """, user.getSecondName(), user.getFirstName(), user.getPatronymic(), host, user.getActivationCode());

//        try {
//            mailSender.send(user.getEmail(), "Активация аккаунта", message);
//        } catch (MailSendException e) {
//            throw new SendMailException();
//        } catch (MessagingException e) {
        //fixme add log
//            throw new RuntimeException(e);
//        }
    }

    public void activateUser(String code) {
        User user = userRepo.findByActivationCode(code).orElseThrow(() -> new UserNotFoundException("Activation code is invalid"));
        user.setActivationCode(null);
        userRepo.save(user);
    }

    public void updateUserData(EditUserDataDto editInsuranceDataDto, String password) {
        if (editInsuranceDataDto.getEmail() != null &&
                !editInsuranceDataDto.getEmail().isBlank()) {
            User user = auth.getUser();
            if (!user.getEmail().equals(editInsuranceDataDto.getEmail())) {
                throwExceptionIfItIsNotMyPassword(password);
                user.setEmail(editInsuranceDataDto.getEmail());
                userRepo.save(user);
            }
        }
    }
}
