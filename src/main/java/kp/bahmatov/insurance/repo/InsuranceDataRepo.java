package kp.bahmatov.insurance.repo;

import kp.bahmatov.insurance.domain.structure.insurance.userdata.InsuranceUserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceDataRepo extends JpaRepository<InsuranceUserData, Integer> {
}
