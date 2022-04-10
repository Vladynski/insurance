package kp.bahmatov.insurance.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;

@Data
public class QuestionInDto {
    private long id;
    @Size(max = 512)
    private String text;
    @Size(max = 1024)
    private String answerText;
}
