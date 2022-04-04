package kp.bahmatov.insurance.domain.structure.insurance;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Payment {
    @Id
    @Column(name = "id")
    private String paymentId;

    @Column(nullable = false)
    private float sum;
    @Column(nullable = false)
    private LocalDateTime deadline;

    private float paidSum;
    private LocalDateTime paidDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @OneToOne
    private Insurance insurance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Payment payment = (Payment) o;
        return paymentId != null && Objects.equals(paymentId, payment.paymentId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
