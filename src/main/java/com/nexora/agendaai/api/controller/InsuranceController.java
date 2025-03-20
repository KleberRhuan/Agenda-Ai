package com.nexora.agendaai.api.controller;

import com.nexora.agendaai.domain.service.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/insurances")
@RequiredArgsConstructor
public class InsuranceController {
    
    private final InsuranceService insuranceService;
}
