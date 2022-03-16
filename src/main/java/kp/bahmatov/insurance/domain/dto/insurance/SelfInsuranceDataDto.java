package kp.bahmatov.insurance.domain.dto.insurance;

import kp.bahmatov.insurance.domain.structure.InsuranceData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SelfInsuranceDataDto {
    String phone;
    String passportId;
    String status;

    public SelfInsuranceDataDto(InsuranceData insuranceData) {
        this.phone = insuranceData.getPhone();
        this.passportId = insuranceData.getPassportId();
        this.status = insuranceData.getStatus().name();
    }
}
