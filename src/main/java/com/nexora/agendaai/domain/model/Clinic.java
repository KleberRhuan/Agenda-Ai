package com.nexora.agendaai.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "clinic_schema")
public class Clinic {
    @Id
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Embedded
    private Address address;
    
    private String phone;
    
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    
    
}
