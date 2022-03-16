package kp.bahmatov.insurance.repo;

import kp.bahmatov.insurance.domain.structure.InsuranceData;
import org.springframework.data.repository.CrudRepository;

public interface InsuranceDataRepo extends CrudRepository<InsuranceData, Integer> {
}
