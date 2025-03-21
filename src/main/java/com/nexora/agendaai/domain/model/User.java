package com.nexora.agendaai.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "user_schema",  name = "users")
public class User {
    enum Role {PATIENT, RECEPTIONIST, PROFESSIONAL, ADMIN}
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstName;
    
    private String lastName;
    
    private String cpf;
    
    private LocalDate dateOfBirth;
    
    @Embedded
    private Address address;
    
    private String phone;
    
    private String email;
    
    @ManyToOne
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;
    
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
