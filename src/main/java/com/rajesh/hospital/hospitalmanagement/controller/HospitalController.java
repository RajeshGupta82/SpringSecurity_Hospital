package com.rajesh.hospital.hospitalmanagement.controller;

import com.rajesh.hospital.hospitalmanagement.dto.DoctorResponseDto;
import com.rajesh.hospital.hospitalmanagement.service.AppointmentService;
import com.rajesh.hospital.hospitalmanagement.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
public class HospitalController {

    private final DoctorService doctorService;
    
    
    public HospitalController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }
}
