package kp.bahmatov.insurance.domain.structure.insurance.userdata;

import kp.bahmatov.insurance.domain.structure.User;
import kp.bahmatov.insurance.domain.structure.insurance.content.Content;
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
public class InsuranceUserData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(unique = true, length = 12)
    private String phone;
    @Column(unique = true, length = 14)
    private String passportId;
    @OneToOne(cascade = CascadeType.ALL)
    private Content photo;
    @Enumerated(value = EnumType.STRING)
    private InsuranceUserDataStatus status;
    @OneToOne
    private User owner;

    public InsuranceUserData(User owner) {
        this.owner = owner;
        this.status = InsuranceUserDataStatus.NONE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InsuranceUserData that = (InsuranceUserData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
