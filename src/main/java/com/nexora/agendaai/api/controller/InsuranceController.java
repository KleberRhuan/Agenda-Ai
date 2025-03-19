package com.nexora.agendaai.api.controller;

import com.nexora.agendaai.domain.dto.InsuranceDTO;
import com.nexora.agendaai.domain.model.Insurance;
import com.nexora.agendaai.domain.repository.InsuranceRepository;
import com.nexora.agendaai.domain.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/insurances")
@RequiredArgsConstructor
public class InsuranceController {
    
    private final InsuranceService insuranceService;
    private final InsuranceRepository insuranceRepository;
    
    @GetMapping
    public ResponseEntity<List<Insurance>> getInsurances() {
        return ResponseEntity.ok(insuranceRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Insurance> getInsurance(@PathVariable Long id) {
        Insurance insurance = insuranceService.getInsuranceById(id);
        return ResponseEntity.ok(insurance);
    }
    
    @PostMapping
    public ResponseEntity<Insurance> createInsurance(@RequestBody InsuranceDTO insurance) {
        return ResponseEntity.ok(insuranceService
                .createInsurance(insurance));
    }
}
