package kp.bahmatov.insurance.service;

import kp.bahmatov.insurance.domain.dto.QuestionInDto;
import kp.bahmatov.insurance.domain.structure.Question;
import kp.bahmatov.insurance.domain.structure.User;
import kp.bahmatov.insurance.repo.QuestionRepo;
import kp.bahmatov.insurance.service.interfaces.Auth;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class QuestionService {
    private final QuestionRepo questionRepo;

    public QuestionService(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }

    public Iterable<Question> getAll() {
        return questionRepo.findAll();
    }


    public void addQuestion(User author, QuestionInDto dto) {
        if (dto.getText() != null && !dto.getText().isBlank()) {
            Question question = new Question();
            question.setOwner(author);
            question.setText(dto.getText());
            question.setSendDateTime(LocalDateTime.now());
            questionRepo.save(question);
        }
    }
}
