package kp.bahmatov.insurance.domain.util.setting;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public enum SettingType {
    @Enumerated(EnumType.STRING)
    INTEGER, DOUBLE, BOOLEAN
}
