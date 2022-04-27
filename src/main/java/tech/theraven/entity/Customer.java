package tech.theraven.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "serial")
    private Long id;

    @JsonIgnore
    private LocalDateTime created;
    @JsonIgnore
    private LocalDateTime updated;
    @Length(min = 2, max = 50)
    @NotNull
    private String fullName;
    @Length(min = 2, max = 100)
    @Email
    @NotNull
    private String email;
    @Length(min = 6, max = 14)
    @Pattern(regexp = "^\\+(\\(\\d{2,4}\\)|\\d{2,4})?\\d{2,4}?\\d{2,4}$")
    private String phone;

    @JsonIgnore
    boolean isActive;


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return id != null && Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
