package kp.bahmatov.insurance.domain.dto.selection.update;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
public class SelectionVariantUpdateDto {
    @Min(value = 0, message = "Id не может быть меньше нуля")
    private long id;
    private String name;
    @Pattern(regexp = "\\d*(\\.\\d{1,3})?", message = "Коэффициент не может иметь точность более 3 знаков после запятой")
    private String coefficient;
    @Min(value = 0, message = "Id группы не может быть меньше нуля")
    private int groupId;

    public float getCoefficient() {
        if (coefficient == null)
            return 0;
        return Float.parseFloat(coefficient);
    }
}
