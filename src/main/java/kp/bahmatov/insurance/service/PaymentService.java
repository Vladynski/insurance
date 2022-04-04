package kp.bahmatov.insurance.service;

import kp.bahmatov.insurance.domain.structure.insurance.Insurance;
import kp.bahmatov.insurance.domain.structure.insurance.Payment;
import kp.bahmatov.insurance.domain.structure.insurance.PaymentStatus;
import kp.bahmatov.insurance.repo.PaymentRepo;
import kp.bahmatov.insurance.util.CodeGenerator;
import kp.bahmatov.insurance.util.DateTimeUtil;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final PaymentRepo paymentRepo;

    public PaymentService(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    public Payment createPayment(Insurance insurance, float sum) {
        Payment payment = new Payment();
        payment.setInsurance(insurance);
        payment.setDeadline(DateTimeUtil.getStartNextDay());
        payment.setStatus(PaymentStatus.WAIT);
        payment.setSum(sum);
        payment.setPaymentId(getPaymentId());

        return payment;
    }


    /**
     * В будущем это может быть ID платежа в системе, номер платежа в каком-нибудь ЕРИП и т.п.
     * @return а пока что это просто строка
     * */
    private String getPaymentId() {
        return CodeGenerator.generateCode();
    }
}
