package kp.bahmatov.insurance.repo;

import kp.bahmatov.insurance.domain.structure.Faq;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaqRepo extends CrudRepository<Faq, Integer> {
    List<Faq> findAll();
}
