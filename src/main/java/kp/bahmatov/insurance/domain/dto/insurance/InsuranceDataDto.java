package kp.bahmatov.insurance.domain.dto.insurance;

import kp.bahmatov.insurance.domain.structure.InsuranceData;
import lombok.Getter;

@Getter
public class InsuranceDataDto extends SelfInsuranceDataDto{
    private final int id;
    private final int owner;

    public InsuranceDataDto(InsuranceData insuranceData) {
        super(insuranceData);
        this.id = insuranceData.getId();
        this.owner = insuranceData.getOwner().getId();
    }
}
