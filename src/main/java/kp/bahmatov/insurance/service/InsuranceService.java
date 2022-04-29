package kp.bahmatov.insurance.service;

import kp.bahmatov.insurance.domain.dto.filter.InsuranceFilterDto;
import kp.bahmatov.insurance.domain.dto.insurance.InsuranceInDto;
import kp.bahmatov.insurance.domain.structure.insurance.Insurance;
import kp.bahmatov.insurance.domain.structure.insurance.InsuranceStatus;
import kp.bahmatov.insurance.domain.structure.insurance.Payment;
import kp.bahmatov.insurance.domain.structure.insurance.selection.SelectionVariant;
import kp.bahmatov.insurance.exceptions.BadRequestException;
import kp.bahmatov.insurance.repo.InsuranceRepo;
import kp.bahmatov.insurance.repo.InsuranceUserDataRepo;
import kp.bahmatov.insurance.repo.UserRepo;
import kp.bahmatov.insurance.repo.specification.SpecificationBuilder;
import kp.bahmatov.insurance.repo.specification.StructureSpecification;
import kp.bahmatov.insurance.repo.specification.reflection.SpecificBuilderByDto;
import kp.bahmatov.insurance.service.interfaces.Auth;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@EnableScheduling
public class InsuranceService {
    private final Auth auth;
    private final InsuranceRepo insuranceRepo;
    private final SelectionService selectionService;
    private final PaymentService paymentService;
    private final InsuranceUserDataRepo insuranceuserDataRepo;
    private final UserRepo userRepo;

    public InsuranceService(Auth auth,
                            InsuranceRepo insuranceRepo,
                            SelectionService selectionService,
                            PaymentService paymentService,
                            InsuranceUserDataRepo insuranceuserDataRepo,
                            UserRepo userRepo) {
        this.auth = auth;
        this.insuranceRepo = insuranceRepo;
        this.selectionService = selectionService;
        this.paymentService = paymentService;
        this.insuranceuserDataRepo = insuranceuserDataRepo;
        this.userRepo = userRepo;
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
        insurance.setEndTime(getInsuranceDeadline());
        insurance.setVariants(List.of(insuranceDto.getSelectionVariantsIds()));

        List<SelectionVariant> variants = selectionService.findByIds(List.of(insuranceDto.getSelectionVariantsIds()));
        float sum = calculate(variants);

        Payment payment = paymentService.createPayment(insurance, sum);

        insurance.setPayment(payment);

        insuranceRepo.save(insurance);
    }

    private LocalDateTime getInsuranceDeadline() {
        return LocalDateTime.now()
                .plusMonths(6)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void checkInsuranceDeadline() {
        List<Insurance> found = insuranceRepo.findAllByEndTimeBeforeAndStatus(LocalDateTime.now(), InsuranceStatus.VALID);
        found.forEach(el -> el.setStatus(InsuranceStatus.END));
        insuranceRepo.saveAll(found);
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

        Set<Long> groupIds = new HashSet<>();
        for (SelectionVariant variant : variants) {
            groupIds.add(variant.getId());
        }

        if (groupIds.size() != variants.size())
            throw new BadRequestException("В запросе есть два или более варианта из одной группы");
    }

    public List<Insurance> getAllMy() {
        return insuranceRepo.findAllByCreator(auth.getUser().getInsuranceData());
    }

    public List<Insurance> filter(InsuranceFilterDto filterDto) {
        if (filterDto.getId() != null)
            return insuranceRepo.findById(filterDto.getId()).map(List::of).orElse(Collections.emptyList());

        SpecificationBuilder<Insurance> builder = new SpecificationBuilder<>(StructureSpecification::new);
        SpecificBuilderByDto.fillingBuilder(builder, filterDto);

        if (filterDto.getUserId() != null) {
            userRepo.findById(filterDto.getUserId())
                    .ifPresent(user -> builder.with("creator", user.getInsuranceData().getId()));
        }

        if (filterDto.getPassportId() != null) {
            insuranceuserDataRepo.findByPassportId(filterDto.getPassportId())
                    .ifPresent(insuranceData -> builder.with("creator", insuranceData.getId()));
        }

        if (filterDto.getPhone() != null) {
            insuranceuserDataRepo.findByPhone(filterDto.getPhone())
                    .ifPresent(insuranceData -> builder.with("creator", insuranceData.getId()));
        }


        Specification<Insurance> specification = builder.build();

        return insuranceRepo.findAll(specification);
    }
}
