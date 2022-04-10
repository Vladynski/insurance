package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.domain.dto.QuestionInDto;
import kp.bahmatov.insurance.domain.structure.User;
import kp.bahmatov.insurance.exceptions.SendRequestTimeoutException;
import kp.bahmatov.insurance.service.QuestionService;
import kp.bahmatov.insurance.service.SettingsService;
import kp.bahmatov.insurance.service.interfaces.Auth;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final Map<Integer, Long> senders = new HashMap<>();
    private final int sendTimeout = SettingsService.getSendQuestionTimeout().getIntValue() * 60 * 1000;

    private final QuestionService questionService;
    private final Auth auth;

    public QuestionController(QuestionService questionService, Auth auth) {
        this.questionService = questionService;
        this.auth = auth;
    }

    //FIXME
    @GetMapping
    public void getAll() {
        questionService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addQuestion(@Valid @RequestBody QuestionInDto questionDto) {
        User author = auth.getUser();
        Long lastActivity = senders.get(author.getId());
        if (lastActivity == null ||
                lastActivity + sendTimeout < System.currentTimeMillis()) {
            questionService.addQuestion(author, questionDto);
            senders.put(author.getId(), System.currentTimeMillis());
        } else {
            throw new SendRequestTimeoutException(String.format("Вопросы можно задавать раз в %d минут", SettingsService.getSendQuestionTimeout().getIntValue()));
        }
    }
}
