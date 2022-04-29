package kp.bahmatov.insurance.domain.dto;

import kp.bahmatov.insurance.domain.util.setting.SettingStructure;
import kp.bahmatov.insurance.domain.util.setting.SettingType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SettingsDto {
    @NotNull
    @NotBlank
    private String key;
    private String value;
    private String description;
    private SettingType type;

    public SettingsDto(SettingStructure structure) {
        this.key = structure.getKey();
        this.value = structure.getValue();
        this.description = structure.getDescription();
        this.type = structure.getType();
    }
}
