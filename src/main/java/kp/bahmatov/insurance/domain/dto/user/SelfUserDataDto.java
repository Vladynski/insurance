package kp.bahmatov.insurance.domain.dto.user;

import kp.bahmatov.insurance.domain.dto.insurance.SelfInsuranceDataDto;
import kp.bahmatov.insurance.domain.structure.Role;
import kp.bahmatov.insurance.domain.structure.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SelfUserDataDto {
    String firstName;
    String secondName;
    String patronymic;
    String email;
    LocalDateTime registrationDate;
    Set<Role> roles;
    SelfInsuranceDataDto insurance;

    public SelfUserDataDto(User user) {
        this.firstName = user.getFirstName();
        this.secondName = user.getSecondName();
        this.patronymic = user.getPatronymic();
        this.email = user.getEmail();
        this.registrationDate = user.getRegistrationDate();
        this.roles = user.getRoles();
        this.insurance = new SelfInsuranceDataDto(user.getInsurance());
    }
}
