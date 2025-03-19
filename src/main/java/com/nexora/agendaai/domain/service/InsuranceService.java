package com.nexora.agendaai.domain.service;

import com.nexora.agendaai.domain.dto.InsuranceDTO;
import com.nexora.agendaai.domain.exception.InsuranceNotFoundException;
import com.nexora.agendaai.domain.model.Insurance;
import com.nexora.agendaai.domain.repository.InsuranceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    
    public Insurance getInsuranceById(Long id) {
        return insuranceRepository.findById(id).orElseThrow(() -> 
                new InsuranceNotFoundException("Insurance not found"));
    }
    
    @Transactional
    public Insurance createInsurance(InsuranceDTO insurance) {
        Insurance insuranceEntity = Insurance.builder().name(insurance.name()).build();
        return insuranceRepository.save(insuranceEntity);
    }
    
    
}
