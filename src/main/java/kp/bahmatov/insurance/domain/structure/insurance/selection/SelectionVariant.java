package kp.bahmatov.insurance.domain.structure.insurance.selection;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SelectionVariant {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne(targetEntity = SelectionGroup.class, fetch = FetchType.LAZY)
    @ToString.Exclude
    private SelectionGroup group;
    @Column(length = 64)
    private String name;
    private float coefficient;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SelectionVariant that = (SelectionVariant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
