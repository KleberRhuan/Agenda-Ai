package com.nexora.agendaai.domain.service;

import com.nexora.agendaai.domain.exception.InsuranceNotFoundException;
import com.nexora.agendaai.domain.model.Insurance;
import com.nexora.agendaai.domain.repository.InsuranceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;


    public Insurance findById(Long id) {
        return insuranceRepository.findById(id)
                .orElseThrow(() -> new InsuranceNotFoundException(id)); 
    }
}
