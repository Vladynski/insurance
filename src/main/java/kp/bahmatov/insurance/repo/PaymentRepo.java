package kp.bahmatov.insurance.repo;

import kp.bahmatov.insurance.domain.structure.insurance.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends CrudRepository<Payment, String> {
}
