package kp.bahmatov.insurance.domain.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class SendMailInDto {
    @Min(value = 1, message = "Id не может быть меньше единицы")
    private int id;
    @NotBlank(message = "Сообщение не может быть пустым")
    private String text;
}
