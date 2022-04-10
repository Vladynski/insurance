package kp.bahmatov.insurance.domain.dto.edit;

import kp.bahmatov.insurance.domain.structure.insurance.userdata.InsuranceUserDataStatus;
import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
public class EditUserDataDto {
    @Min(value = 1, message = "ID не может быть меньше еденицы")
    private Integer id;
    @Pattern(regexp = "[а-яА-Яa-zA-Z]+")
    private String firstName;
    @Pattern(regexp = "[а-яА-Яa-zA-Z]+")
    private String secondName;
    @Pattern(regexp = "[а-яА-Яa-zA-Z]+")
    private String patronymic;
    @Email(message = "Неверный формат почты")
    private String email;
    @Pattern(regexp = "375\\d\\d\\d\\d\\d\\d\\d\\d\\d", message = "Неверный формат: 375 ЦЦ ЦЦЦ ЦЦ ЦЦ")
    private String phone;
    @Pattern(regexp = "\\d\\d\\d\\d\\d\\d\\d[A-Z]\\d\\d\\d[A-Z]{2}\\d", message = "Неверный формат: Ц ЦЦЦЦЦЦ Б ЦЦЦ ББ Ц")
    private String passportId;
    private Boolean adminStatus;
    private InsuranceUserDataStatus insuranceDataStatus;

    public boolean isContainsChanges() {
        return email != null ||
                firstName != null ||
                secondName != null ||
                patronymic != null ||
                phone != null ||
                passportId != null ||
                adminStatus != null ||
                insuranceDataStatus != null;
    }
}
