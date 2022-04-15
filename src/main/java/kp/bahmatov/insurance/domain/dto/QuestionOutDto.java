package kp.bahmatov.insurance.domain.dto;

import kp.bahmatov.insurance.domain.structure.Question;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionOutDto {
    private long id;
    private String text;
    private String textAnswer;

    public QuestionOutDto(Question question) {
        this.id = question.getId();
        this.text = question.getText();
        this.textAnswer = question.getTextAnswer();
    }
}
