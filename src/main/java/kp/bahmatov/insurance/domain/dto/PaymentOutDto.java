package kp.bahmatov.insurance.domain.dto;

import kp.bahmatov.insurance.domain.structure.insurance.Payment;
import kp.bahmatov.insurance.domain.structure.insurance.PaymentStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentOutDto {
    private final String id;
    private final float sum;
    private final LocalDateTime deadline;
    private final float paidSum;
    private final LocalDateTime paidDate;
    private final PaymentStatus status;
    private final long insuranceId;

    public PaymentOutDto(Payment payment, long insuranceId) {
        this.id = payment.getPaymentId();
        this.sum = payment.getSum();
        this.deadline = payment.getDeadline();
        this.paidSum = payment.getPaidSum();
        this.paidDate = payment.getPaidDate();
        this.status = payment.getStatus();
        this.insuranceId = insuranceId;
    }
}
