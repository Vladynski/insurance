package kp.bahmatov.insurance.domain.dto.insurance.userdata;

import kp.bahmatov.insurance.domain.dto.ContentDto;
import kp.bahmatov.insurance.domain.structure.insurance.userdata.InsuranceUserData;
import kp.bahmatov.insurance.domain.structure.insurance.userdata.InsuranceUserDataStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@ToString
public class InsuranceUserDataDto {
    @Pattern(regexp = "375\\d\\d\\d\\d\\d\\d\\d\\d\\d", message = "Неверный формат: 375 ЦЦ ЦЦЦ ЦЦ ЦЦ")
    private String phone;
    @Pattern(regexp = "\\d\\d\\d\\d\\d\\d\\d[A-Z]\\d\\d\\d[A-Z]{2}\\d", message = "Неверный формат: Ц ЦЦЦЦЦЦ Б ЦЦЦ ББ Ц")
    private String passportId;
    private ContentDto photo;
    private InsuranceUserDataStatus status;

    public InsuranceUserDataDto(InsuranceUserData insuranceUserData) {
        this.phone = insuranceUserData.getPhone();
        this.passportId = insuranceUserData.getPassportId();
        this.photo = insuranceUserData.getPhoto() == null ? null : new ContentDto(insuranceUserData.getPhoto());
        this.status = insuranceUserData.getStatus();
    }
}
