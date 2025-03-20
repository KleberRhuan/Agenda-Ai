package com.nexora.agendaai.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

@Embeddable
public class Address {
    private String street;
    private String number;
    private String city;
    private String state;
    private String zipCode;
    
}
