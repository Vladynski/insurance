package kp.bahmatov.insurance.domain.dto.insurance.userdata;

import kp.bahmatov.insurance.domain.structure.insurance.userdata.InsuranceUserData;
import kp.bahmatov.insurance.domain.structure.insurance.userdata.InsuranceUserDataStatus;
import lombok.Getter;

@Getter
@Deprecated
public class InsuranceUserDataOutDto extends InsuranceUserDataDto {
    private final int id;
    private final int owner;
    private final InsuranceUserDataStatus status;

    public InsuranceUserDataOutDto(InsuranceUserData insuranceUserData) {
        super(insuranceUserData);
        this.id = insuranceUserData.getId();
        this.owner = insuranceUserData.getOwner().getId();
        this.status = insuranceUserData.getStatus();
    }
}
