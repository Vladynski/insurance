package kp.bahmatov.insurance.domain.dto;

import kp.bahmatov.insurance.domain.structure.Faq;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class FaqDto {
    @NotBlank
    @Size(max = 255)
    private String question;
    @NotBlank
    @Size(max = 1024)
    private String answer;

    public FaqDto(Faq faq) {
        this.question = faq.getQuestion();
        this.answer = faq.getAnswer();
    }
}
