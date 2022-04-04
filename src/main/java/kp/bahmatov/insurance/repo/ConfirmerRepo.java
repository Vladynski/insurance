package kp.bahmatov.insurance.repo;

import kp.bahmatov.insurance.domain.util.Confirmer;
import kp.bahmatov.insurance.domain.structure.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmerRepo extends CrudRepository<Confirmer, Long> {
    Confirmer findByOwner(User owner);
}
