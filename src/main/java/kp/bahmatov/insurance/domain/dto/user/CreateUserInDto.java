package kp.bahmatov.insurance.domain.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserInDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String secondName;
    @NotBlank
    private String patronymic;
    @Email(message = "Неверный формат почты")
    @NotBlank
    private String email;
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*_#?&])[A-Za-z\\d@$!%*_#?&]{8,}$",
            message = "Пароль также должен содержать цифру и спец символ, длину более семи"
    )
    private String password;
}
