package com.nexora.agendaai.api.controller;

import com.nexora.agendaai.domain.model.Insurance;
import com.nexora.agendaai.domain.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/insurances")
@RequiredArgsConstructor
public class InsuranceController {
    
    private final InsuranceService insuranceService;
    
    @GetMapping("{id}")
    public ResponseEntity<Insurance> findById(@PathVariable Long id) {
        Insurance insurance = insuranceService.findById(id);
        return ResponseEntity.ok(insurance);
    }
}
