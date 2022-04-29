package kp.bahmatov.insurance.domain.util.setting;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
    @Column(length = 8192)
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
