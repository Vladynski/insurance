package kp.bahmatov.insurance.domain.structure.insurance;

import kp.bahmatov.insurance.domain.structure.insurance.userdata.InsuranceUserData;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    private InsuranceUserData creator;
    private boolean creatorIsOwner;
    private String ownerFirstName;
    private String ownerSecondName;
    private String ownerPatronymic;
    private String winNumber;
    private String registrationNumber;
    private InsuranceStatus insuranceStatus;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Insurance insurance = (Insurance) o;
        return Objects.equals(id, insurance.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
