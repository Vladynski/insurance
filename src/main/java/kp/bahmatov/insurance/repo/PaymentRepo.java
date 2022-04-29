package kp.bahmatov.insurance.repo;

import kp.bahmatov.insurance.domain.structure.insurance.Payment;
import kp.bahmatov.insurance.domain.structure.insurance.PaymentStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepo extends CrudRepository<Payment, String> {
    List<Payment> findAllByDeadlineBeforeAndStatus(LocalDateTime before, PaymentStatus status);
}
