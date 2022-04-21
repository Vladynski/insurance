package kp.bahmatov.insurance.service;

import kp.bahmatov.insurance.domain.dto.edit.EditUserDataDto;
import kp.bahmatov.insurance.domain.dto.filter.UserFilter;
import kp.bahmatov.insurance.domain.dto.user.CreateUserInDto;
import kp.bahmatov.insurance.domain.structure.Role;
import kp.bahmatov.insurance.domain.structure.User;
import kp.bahmatov.insurance.domain.structure.insurance.content.Content;
import kp.bahmatov.insurance.domain.structure.insurance.content.ContentType;
import kp.bahmatov.insurance.domain.structure.insurance.userdata.InsuranceUserData;
import kp.bahmatov.insurance.domain.structure.insurance.userdata.InsuranceUserDataStatus;
import kp.bahmatov.insurance.exceptions.ForbiddenException;
import kp.bahmatov.insurance.exceptions.PasswordConfirmationException;
import kp.bahmatov.insurance.exceptions.UserAlreadyExistsException;
import kp.bahmatov.insurance.exceptions.UserNotFoundException;
import kp.bahmatov.insurance.repo.InsuranceDataRepo;
import kp.bahmatov.insurance.repo.UserRepo;
import kp.bahmatov.insurance.repo.specification.SpecificationBuilder;
import kp.bahmatov.insurance.repo.specification.StructureSpecification;
import kp.bahmatov.insurance.repo.specification.reflection.SpecificBuilderByDto;
import kp.bahmatov.insurance.service.interfaces.Auth;
import kp.bahmatov.insurance.util.CodeGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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
            user.setEmail("admin@admin.ru");
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
            throw new UserAlreadyExistsException("На этот Email уже зарегистрирован аккаунт");

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
        User self = auth.getUser();
        if (isAdmin(self)) {
            updateOtherData(self, editInsuranceDataDto);
        } else {
            updateSelfData(self, editInsuranceDataDto, password);
        }
    }

    private void updateOtherData(User self, EditUserDataDto editDto) {
        if (editDto.isContainsChanges()) {
            Integer userId = editDto.getId();
            User edit;
            if (userId == null)
                edit = self;
            else
                edit = userRepo.findById(userId).orElseThrow(UserNotFoundException::new);

            if (editDto.getFirstName() != null)
                edit.setFirstName(editDto.getFirstName());
            if (editDto.getSecondName() != null)
                edit.setSecondName(editDto.getSecondName());
            if (editDto.getPatronymic() != null)
                edit.setPatronymic(editDto.getPatronymic());
            if (editDto.getEmail() !=    null)
                edit.setEmail(editDto.getEmail());
            if (editDto.getPhone() != null)
                edit.getInsuranceData().setPhone(editDto.getPhone());
            if (editDto.getPassportId() != null)
                edit.getInsuranceData().setPassportId(editDto.getPassportId());
            if (editDto.getAdminStatus() != null) {
                if (editDto.getAdminStatus()) {
                    if (!isAdmin(edit))
                        edit.getRoles().add(Role.ADMIN);
                } else {
                    if (isAdmin(edit))
                        edit.getRoles().remove(Role.ADMIN);
                }
            }
            if (editDto.getInsuranceDataStatus() != null)
                edit.getInsuranceData().setStatus(editDto.getInsuranceDataStatus());


            insuranceDataRepo.save(edit.getInsuranceData());
            userRepo.save(edit);
        }
    }

    private void updateSelfData(User self, EditUserDataDto editDto, String password) {
        if (editDto.isContainsChanges()) {
            throwExceptionIfItIsNotMyPassword(password);
            if (editDto.getEmail() != null)
                self.setEmail(editDto.getEmail());
            if (editDto.getPhone() != null)
                self.getInsuranceData().setPhone(editDto.getPhone());
            insuranceDataRepo.save(self.getInsuranceData());
            userRepo.save(self);
        }
    }

    public List<User> filter(UserFilter userFilter) {
        if (userFilter.getPassportId() != null) {
            List<User> resultList = new ArrayList<>(1);
            insuranceDataRepo.findByPassportId(userFilter.getPassportId()).ifPresent(el -> resultList.add(el.getOwner()));
            return resultList;
        }

        SpecificationBuilder<User> builder = new SpecificationBuilder<>(StructureSpecification::new);

        SpecificBuilderByDto.fillingBuilder(builder, userFilter);

        Specification<User> specification = builder.build();

        List<User> result = userRepo.findAll(specification);

        if (userFilter.getAdmin() != null && userFilter.getAdmin()) {
            result = result.stream().filter(this::isAdmin).toList();
        }

        return result;
    }

    public void ban(Integer userId) {
        if (userId != null) {
            User user = userRepo.findById(userId).orElseThrow(UserNotFoundException::new);
            if (isAdmin(user))
                throw new ForbiddenException("Нельзя забанить пользователя со статусом ADMIN");
            user.setActivationCode(CodeGenerator.generateCode());
        }
    }

    private boolean isAdmin(User user) {
        return user.getRoles().contains(Role.ADMIN);
    }

    public User getSelfData() {
        return userRepo.findById(auth.getUser().getId()).get();
    }
}
