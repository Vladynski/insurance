package kp.bahmatov.insurance.domain.structure;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usr")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(length = 32)
    private String firstName;
    @Column(length = 32)
    private String secondName;
    @Column(length = 32)
    private String patronymic;
    @Column(unique = true)
    private String email;
    private String password;
    private String activationCode;
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,  pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registrationDate;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private InsuranceData insurance;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activationCode == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
