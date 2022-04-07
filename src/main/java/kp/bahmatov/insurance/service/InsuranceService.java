package kp.bahmatov.insurance.service;

import kp.bahmatov.insurance.domain.dto.insurance.InsuranceInDto;
import kp.bahmatov.insurance.domain.structure.insurance.Insurance;
import kp.bahmatov.insurance.domain.structure.insurance.InsuranceStatus;
import kp.bahmatov.insurance.domain.structure.insurance.Payment;
import kp.bahmatov.insurance.domain.structure.insurance.selection.SelectionVariant;
import kp.bahmatov.insurance.exceptions.BadRequestException;
import kp.bahmatov.insurance.repo.InsuranceRepo;
import kp.bahmatov.insurance.service.interfaces.Auth;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class InsuranceService {
    private final Auth auth;
    private final InsuranceRepo insuranceRepo;
    private final SelectionService selectionService;
    private final PaymentService paymentService;

    public InsuranceService(Auth auth,
                            InsuranceRepo insuranceRepo,
                            SelectionService selectionService,
                            PaymentService paymentService) {
        this.auth = auth;
        this.insuranceRepo = insuranceRepo;
        this.selectionService = selectionService;
        this.paymentService = paymentService;
    }


    public void createInsurance(InsuranceInDto insuranceDto) {
        throwExceptionIfInsuranceAlreadyExists(insuranceDto);

        Insurance insurance = new Insurance();
        insurance.setCreator(auth.getUser().getInsuranceData());
        insurance.setCreatorIsOwner(insuranceDto.getCreatorIsOwner());
        insurance.setOwnerFirstName(insuranceDto.getOwnerFirstName());
        insurance.setOwnerSecondName(insuranceDto.getOwnerSecondName());
        insurance.setOwnerPatronymic(insuranceDto.getOwnerPatronymic());
        insurance.setWinNumber(insuranceDto.getWinNumber());
        insurance.setRegistrationNumber(insuranceDto.getRegistrationNumber());
        insurance.setStatus(InsuranceStatus.AWAITING_PAYMENT);
        insurance.setEndTime(LocalDateTime.now().plusMonths(6));
        insurance.setVariants(Arrays.asList(insuranceDto.getSelectionVariantsIds()));

        List<SelectionVariant> variants = selectionService.findByIds(List.of(insuranceDto.getSelectionVariantsIds()));
        float sum = calculate(variants);

        Payment payment = paymentService.createPayment(insurance, sum);

        insurance.setPayment(payment);

        insuranceRepo.save(insurance);
    }

    public void throwExceptionIfInsuranceAlreadyExists(InsuranceInDto insuranceDto) {
        Insurance foundInsurance = insuranceRepo.findInsuranceByWinNumber(insuranceDto.getWinNumber()).orElse(null);
        if (foundInsurance != null &&
                (foundInsurance.getStatus() == InsuranceStatus.VALID ||
                foundInsurance.getStatus() == InsuranceStatus.AWAITING_PAYMENT))
            throw new BadRequestException("Страховка на это транспортное средство уже оформлена и действует, или ожидает оплаты.");
    }

    public float calculate(List<SelectionVariant> variants) {
        throwExceptionIfInvalidVariants(variants);

        float base = SettingsService.getBaseSum().getFloatValue();
        float resultSum = base;
        for (SelectionVariant variant : variants) {
            resultSum += base * variant.getCoefficient() - base;
        }
        return new BigDecimal(resultSum).setScale(2, RoundingMode.HALF_UP).floatValue();
    }

    private void throwExceptionIfInvalidVariants(List<SelectionVariant> variants) {
        long groupCount = selectionService.getGroupCount();
        if (groupCount != variants.size())
            throw new BadRequestException("В запросе должно быть по одному варианту из каждой группы выбора");

        Set<Integer> groupIds = new HashSet<>();
        for (SelectionVariant variant : variants) {
            groupIds.add(variant.getId());
        }

        if (groupIds.size() != variants.size())
            throw new BadRequestException("В запросе есть два или более варианта из одной группы");
    }

    public List<Insurance> getAllMy() {
        return insuranceRepo.findAllByCreator(auth.getUser().getInsuranceData());
    }
}
