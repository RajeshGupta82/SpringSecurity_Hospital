package com.rajesh.hospital.hospitalmanagement.controller;

import com.rajesh.hospital.hospitalmanagement.dto.PatientResponseDto;
import com.rajesh.hospital.hospitalmanagement.entity.User;
import com.rajesh.hospital.hospitalmanagement.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final PatientService patientService;
    
    public AdminController(PatientService patientService) {
        this.patientService = patientService;
    }


    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(patientService.getAllPatients(pageNumber, pageSize));
    }
}
