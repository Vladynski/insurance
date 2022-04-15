package kp.bahmatov.insurance.repo;

import kp.bahmatov.insurance.domain.structure.Question;
import kp.bahmatov.insurance.domain.structure.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {
    List<Question> findQuestionByOwner(User user);

    List<Question> findQuestionsByTextAnswerIsNull();
}
