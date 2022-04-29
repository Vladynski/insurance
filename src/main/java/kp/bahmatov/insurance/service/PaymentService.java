package kp.bahmatov.insurance.service;

import kp.bahmatov.insurance.domain.structure.insurance.Insurance;
import kp.bahmatov.insurance.domain.structure.insurance.InsuranceStatus;
import kp.bahmatov.insurance.domain.structure.insurance.Payment;
import kp.bahmatov.insurance.domain.structure.insurance.PaymentStatus;
import kp.bahmatov.insurance.domain.structure.insurance.userdata.InsuranceUserData;
import kp.bahmatov.insurance.exceptions.BadRequestException;
import kp.bahmatov.insurance.exceptions.NotFoundException;
import kp.bahmatov.insurance.repo.InsuranceRepo;
import kp.bahmatov.insurance.repo.PaymentRepo;
import kp.bahmatov.insurance.service.interfaces.Auth;
import kp.bahmatov.insurance.util.CodeGenerator;
import kp.bahmatov.insurance.util.DateTimeUtil;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
public class PaymentService {
    private final PaymentRepo paymentRepo;
    private final InsuranceRepo insuranceRepo;
    private final Auth auth;

    public PaymentService(PaymentRepo paymentRepo, InsuranceRepo insuranceRepo, Auth auth) {
        this.paymentRepo = paymentRepo;
        this.insuranceRepo = insuranceRepo;
        this.auth = auth;
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
     *
     * @return а пока что это просто строка
     */
    private String getPaymentId() {
        return CodeGenerator.generateCode();
    }

    public void pay(String paymentId) {
        Payment payment = paymentRepo.findById(paymentId).orElseThrow(
                () -> new NotFoundException("Платёж с таким id не найден")
        );

        InsuranceUserData creator = payment.getInsurance().getCreator();
        if (!creator.equals(auth.getUser().getInsuranceData())) {
            throw new NotFoundException("Платёж с таким id не найден");
        } else if (payment.getStatus() == PaymentStatus.OVERDUE) {
            throw new BadRequestException("Платёж просрочен");
        } else if (payment.getStatus() == PaymentStatus.PAID) {
            throw new BadRequestException(String.format("Страховка с id %d уже оплачена", payment.getInsurance().getId()));
        }

        payment.setPaidSum(payment.getSum());
        payment.setPaidDate(LocalDateTime.now());
        payment.setStatus(PaymentStatus.PAID);

        Insurance insurance = payment.getInsurance();
        insurance.setStatus(InsuranceStatus.VALID);

        insuranceRepo.save(insurance);
        paymentRepo.save(payment);
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void checkPaymentDeadline() {
        List<Payment> found = paymentRepo.findAllByDeadlineBeforeAndStatus(LocalDateTime.now(), PaymentStatus.WAIT);
        found.forEach(el -> {
            el.setStatus(PaymentStatus.OVERDUE);
            paymentRepo.save(el);
            Insurance insurance = el.getInsurance();
            insurance.setStatus(InsuranceStatus.PAYMENT_OVERDUE);
            insuranceRepo.save(insurance);
        });
    }
}
