package com.nexora.agendaai.domain.exception;

public class InsuranceNotFoundException extends EntityNotFoundException {
    private InsuranceNotFoundException(String message) {
        super(message);
    }
    
    public InsuranceNotFoundException(Long insuranceId) {
        this(String.format("A seguradora com o id %d nao foi encontrada", insuranceId));
    }
}
