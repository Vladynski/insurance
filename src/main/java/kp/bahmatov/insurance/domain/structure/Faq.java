package kp.bahmatov.insurance.domain.structure;

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
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column
    private String question;
    @Column(length = 1024)
    private String answer;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Faq faq = (Faq) o;
        return Objects.equals(id, faq.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
