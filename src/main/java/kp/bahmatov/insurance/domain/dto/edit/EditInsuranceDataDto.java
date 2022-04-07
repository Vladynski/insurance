package kp.bahmatov.insurance.domain.dto.edit;


import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class EditInsuranceDataDto {
    @Pattern(regexp = "(375\\d\\d\\d\\d\\d\\d\\d\\d\\d)", message = "Неверный формат: 375 ЦЦ ЦЦЦ ЦЦ ЦЦ")
    private String phone;
}
