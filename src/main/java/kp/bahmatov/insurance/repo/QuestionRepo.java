package kp.bahmatov.insurance.repo;

import kp.bahmatov.insurance.domain.structure.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepo extends CrudRepository<Question, Long> {
}
