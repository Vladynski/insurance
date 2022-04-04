package kp.bahmatov.insurance.domain.structure.insurance.content;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(length = 32)
    @Enumerated(EnumType.STRING)
    private ContentType type;
    @Type(type="org.hibernate.type.BinaryType")
    @Basic(fetch = FetchType.LAZY)
    @ToString.Exclude
    private byte[] content;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Content content = (Content) o;
        return Objects.equals(id, content.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
