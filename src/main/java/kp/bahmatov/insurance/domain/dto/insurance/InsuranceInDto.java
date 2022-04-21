package kp.bahmatov.insurance.domain.dto.insurance;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class InsuranceInDto {
    @NotNull
    private Boolean creatorIsOwner;
    @NotBlank
    private String ownerFirstName;
    @NotBlank
    private String ownerSecondName;
    @NotBlank
    private String ownerPatronymic;
    //FIXME remove test
    @NotBlank
    @Pattern(regexp = "([0-9A-HJ-NPR-Z]{17})|test", message = "Неверный формат поля")
    private String winNumber;
    @NotBlank
    @Pattern(regexp = "\\d{4}[ABEIKMHOFCTX]{2}\\d?", message = "Неверный формат 4цифры2буквы1цифра_при_наличии")
    private String registrationNumber;
    @NotEmpty
    private Long[] selectionVariantsIds;
}
