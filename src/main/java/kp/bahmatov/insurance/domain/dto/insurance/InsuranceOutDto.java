package kp.bahmatov.insurance.domain.dto.insurance;

import kp.bahmatov.insurance.domain.dto.PaymentOutDto;
import kp.bahmatov.insurance.domain.structure.insurance.Insurance;
import kp.bahmatov.insurance.domain.structure.insurance.InsuranceStatus;
import lombok.Getter;

@Getter
public class InsuranceOutDto {
    private final long id;
    private final boolean creatorIsOwner;
    private final String ownerFirstName;
    private final String ownerSecondName;
    private final String ownerPatronymic;
    private final String winNumber;
    private final String registrationNumber;
    private final InsuranceStatus insuranceStatus;
    private final PaymentOutDto payment;

    private InsuranceOutDto(Insurance insurance) {
        this.id = insurance.getId();
        this.creatorIsOwner = insurance.isCreatorIsOwner();
        this.ownerFirstName = insurance.getOwnerFirstName();
        this.ownerSecondName = insurance.getOwnerSecondName();
        this.ownerPatronymic = insurance.getOwnerPatronymic();
        this.winNumber = insurance.getWinNumber();
        this.registrationNumber = insurance.getRegistrationNumber();
        this.insuranceStatus = insurance.getInsuranceStatus();
        this.payment = new PaymentOutDto(insurance.getPayment(), insurance.getId());
    }
}
