package com.nexora.agendaai.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(schema = "appointment_schema", name = "appointments")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    enum Status {SCHEDULED, CONFIRMED, CANCELLED}
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Instant dateTime;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic professional;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
