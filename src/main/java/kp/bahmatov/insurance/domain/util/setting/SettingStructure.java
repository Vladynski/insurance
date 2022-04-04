package kp.bahmatov.insurance.domain.util.setting;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "settings")
public class SettingStructure implements Setting {
    @Id
    private String key;
    private String value;
    private String description;
    @Enumerated(EnumType.STRING)
    private SettingType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SettingStructure keyValue = (SettingStructure) o;
        return key != null && Objects.equals(key, keyValue.key);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
