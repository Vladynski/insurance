package kp.bahmatov.insurance.domain.structure.insurance.selection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SelectionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(length = 64)
    private String name;
    @OneToMany(targetEntity = SelectionVariant.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "group"
    )
    private List<SelectionVariant> variants = new ArrayList<>();
    private boolean isActive = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SelectionGroup that = (SelectionGroup) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
