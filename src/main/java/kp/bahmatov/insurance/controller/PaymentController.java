package kp.bahmatov.insurance.controller;

import kp.bahmatov.insurance.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @deprecated контроллер предназначен для реализации оплаты заказов в рамках разработки и тестирования
 * функционала оплаты, основная логика оплаты должна быть в Service классе с использованием сторонних
 * сервисов
 */
@Deprecated
@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public void pay(@RequestParam("paymentId") String paymentId) {
        paymentService.pay(paymentId);
    }
}
