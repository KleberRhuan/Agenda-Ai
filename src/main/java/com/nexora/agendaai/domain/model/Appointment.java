package com.nexora.agendaai.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(schema = "appointment_schema")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    enum Status {SCHEDULED, CONFIRMED, CANCELLED}
    
    @Id
    private Long id;
    
    private Instant appointmentDate;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @ManyToOne
    @JoinColumn(name = "professional_id", nullable = false)
    private User professional;
    
    
}
