package kp.bahmatov.insurance.domain.dto.insurance;

import kp.bahmatov.insurance.domain.dto.PaymentOutDto;
import kp.bahmatov.insurance.domain.structure.insurance.Insurance;
import kp.bahmatov.insurance.domain.structure.insurance.InsuranceStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class InsuranceOutDto {
    private final long id;
    private final boolean creatorIsOwner;
    private final String ownerFirstName;
    private final String ownerSecondName;
    private final String ownerPatronymic;
    private final String winNumber;
    private final String registrationNumber;
    private final InsuranceStatus status;
    private final LocalDateTime endTime;
    private final PaymentOutDto payment;
    private final List<Integer> variants;

    public InsuranceOutDto(Insurance insurance) {
        this.id = insurance.getId();
        this.creatorIsOwner = insurance.isCreatorIsOwner();
        this.ownerFirstName = insurance.getOwnerFirstName();
        this.ownerSecondName = insurance.getOwnerSecondName();
        this.ownerPatronymic = insurance.getOwnerPatronymic();
        this.winNumber = insurance.getWinNumber();
        this.registrationNumber = insurance.getRegistrationNumber();
        this.status = insurance.getStatus();
        this.endTime = insurance.getEndTime();
        this.payment = new PaymentOutDto(insurance.getPayment(), insurance.getId());
        this.variants = insurance.getVariants();
    }
}
