package com.nexora.agendaai.domain.dto;
import jakarta.validation.constraints.NotBlank;


public record InsuranceDTO(
        @NotBlank
        String name) {
}
