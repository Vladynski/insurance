package kp.bahmatov.insurance.repo;

import kp.bahmatov.insurance.domain.structure.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByActivationCode(String activationCode);
}
