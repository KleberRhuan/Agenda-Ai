package com.nexora.agendaai.api.controller;


import com.nexora.agendaai.domain.model.Clinic;
import com.nexora.agendaai.domain.repository.ClinicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/clinics")
@RequiredArgsConstructor
public class ClinicController {
    private final ClinicRepository clinicRepository;
    
    @GetMapping
    public ResponseEntity<List<Clinic>> getClinics() {
        return ResponseEntity.ok(clinicRepository.findAll());
    }
    
}
