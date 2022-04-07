package kp.bahmatov.insurance.domain.dto.edit;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class EditUserDataDto {
    @Email(message = "Неверный формат почты")
    private String email;
}
