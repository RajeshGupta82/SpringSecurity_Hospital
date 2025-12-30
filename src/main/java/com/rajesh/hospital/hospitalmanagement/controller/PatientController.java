package com.rajesh.hospital.hospitalmanagement.controller;

import com.rajesh.hospital.hospitalmanagement.dto.AppointmentResponseDto;
import com.rajesh.hospital.hospitalmanagement.dto.CreateAppointmentRequestDto;
import com.rajesh.hospital.hospitalmanagement.dto.PatientResponseDto;
import com.rajesh.hospital.hospitalmanagement.service.AppointmentService;
import com.rajesh.hospital.hospitalmanagement.service.DoctorService;
import com.rajesh.hospital.hospitalmanagement.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;
    
    
    
    public PatientController(PatientService patientService,AppointmentService appointmentService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponseDto> createNewAppointment(@RequestBody CreateAppointmentRequestDto createAppointmentRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createNewAppointment(createAppointmentRequestDto));
    }

    @GetMapping("/profile")
    private ResponseEntity<PatientResponseDto> getPatientProfile() {
        Long patientId = 4L;
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }

}
