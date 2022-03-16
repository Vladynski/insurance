package kp.bahmatov.insurance.domain.dto.user;

import lombok.Getter;

@Getter
public class CreateUserDto {
    private String firstName;
    private String secondName;
    private String patronymic;
    private String email;
    private String password;
}
