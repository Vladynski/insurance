package kp.bahmatov.insurance.domain.dto.user;

import kp.bahmatov.insurance.domain.dto.insurance.userdata.InsuranceUserDataDto;
import kp.bahmatov.insurance.domain.structure.Role;
import kp.bahmatov.insurance.domain.structure.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
public class SelfUserOutDto {
    private String firstName;
    private String secondName;
    private String patronymic;
    private String email;
    private LocalDateTime registrationDate;
    private Set<Role> roles;
    private InsuranceUserDataDto insurance;

    public SelfUserOutDto(User user) {
        this.firstName = user.getFirstName();
        this.secondName = user.getSecondName();
        this.patronymic = user.getPatronymic();
        this.email = user.getEmail();
        this.registrationDate = user.getRegistrationDate();
        this.roles = user.getRoles();
        this.insurance = new InsuranceUserDataDto(user.getInsuranceData());
    }
}
