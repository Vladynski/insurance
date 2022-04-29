package kp.bahmatov.insurance.service;

import kp.bahmatov.insurance.domain.dto.QuestionInDto;
import kp.bahmatov.insurance.domain.structure.Question;
import kp.bahmatov.insurance.domain.structure.User;
import kp.bahmatov.insurance.exceptions.BadRequestException;
import kp.bahmatov.insurance.exceptions.NotFoundException;
import kp.bahmatov.insurance.repo.QuestionRepo;
import kp.bahmatov.insurance.service.interfaces.Auth;
import kp.bahmatov.insurance.util.LetterFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuestionService {
    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    private final QuestionRepo questionRepo;
    private final MailSender mailSender;
    private final Auth auth;

    public QuestionService(QuestionRepo questionRepo, MailSender mailSender, Auth auth) {
        this.questionRepo = questionRepo;
        this.mailSender = mailSender;
        this.auth = auth;
    }

    public List<Question> getAllForUser() {
        return questionRepo.findQuestionByOwner(auth.getUser());
    }


    public List<Question> getAllWithoutAnswer() {
        return questionRepo.findQuestionsByTextAnswerIsNull();
    }

    public long addQuestion(User author, QuestionInDto dto) {
        if (dto.getText() != null && !dto.getText().isBlank()) {
            Question question = new Question();
            question.setOwner(author);
            question.setText(dto.getText());
            question.setSendDateTime(LocalDateTime.now());
            return questionRepo.save(question).getId();
        }
        throw new BadRequestException("Текст вопроса пустой");
    }

    public void setAnswer(long id, String answer) {
        if (answer.isBlank())
            throw new BadRequestException("Текст ответа не может быть пустым");
        if (id < 1)
            throw new BadRequestException("Id не может быть меньше единицы");

        Question question = questionRepo.findById(id).orElseThrow(() -> new NotFoundException("Вопрос с таким id не найден"));
        question.setTextAnswer(answer);
        question.setRespondent(auth.getUser());

        try {
            mailSender.send(
                    question.getOwner().getEmail(),
                    "Ответ на вопрос",
                    LetterFormatter.getLetterWithQuestionAnswer(question.getText(), question.getTextAnswer()));
        } catch (MessagingException e) {
            logger.warn("Не удалось отправить письмо с ответом на вопрос:\n{0}", e);
            //если письмо не отправится, ответ можно будет посмотреть в разделе "Задать вопрос"
        }

        questionRepo.save(question);
    }
}
