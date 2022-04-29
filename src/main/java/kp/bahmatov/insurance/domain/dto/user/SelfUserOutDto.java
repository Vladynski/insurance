package kp.bahmatov.insurance.domain.dto.user;

import kp.bahmatov.insurance.domain.dto.insurance.userdata.InsuranceUserDataDto;
import kp.bahmatov.insurance.domain.structure.Role;
import kp.bahmatov.insurance.domain.structure.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
public class SelfUserOutDto {
    private final String firstName;
    private final String secondName;
    private final String patronymic;
    private final String email;
    private final LocalDateTime registrationDate;
    private final Set<Role> roles;
    private final InsuranceUserDataDto insurance;
    private final int insuranceCount;

    public SelfUserOutDto(User user, int insuranceCount) {
        this.firstName = user.getFirstName();
        this.secondName = user.getSecondName();
        this.patronymic = user.getPatronymic();
        this.email = user.getEmail();
        this.registrationDate = user.getRegistrationDate();
        this.roles = user.getRoles();
        this.insurance = new InsuranceUserDataDto(user.getInsuranceData());
        this.insuranceCount = insuranceCount;
    }
}
