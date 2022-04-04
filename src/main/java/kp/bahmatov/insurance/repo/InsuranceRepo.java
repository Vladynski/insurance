package kp.bahmatov.insurance.repo;

import kp.bahmatov.insurance.domain.structure.insurance.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InsuranceRepo extends JpaRepository<Insurance, Long> {
    Optional<Insurance> findInsuranceByWinNumber(String winNumber);
}
