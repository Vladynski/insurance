package kp.bahmatov.insurance.domain.util;

import kp.bahmatov.insurance.domain.structure.User;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Confirmer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @OneToOne
    private User owner;
    @Column(nullable = false, length = 32)
    private String code;
    private LocalDateTime deadline;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Confirmer confirmer = (Confirmer) o;
        return Objects.equals(id, confirmer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
