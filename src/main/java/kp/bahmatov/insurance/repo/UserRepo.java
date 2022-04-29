package kp.bahmatov.insurance.repo;

import kp.bahmatov.insurance.domain.structure.Role;
import kp.bahmatov.insurance.domain.structure.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Integer>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);

    Optional<User> findByActivationCode(String activationCode);

    List<User> findAllByRolesIn(Collection<Role> role);
}
