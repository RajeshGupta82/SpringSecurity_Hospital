package com.rajesh.hospital.hospitalmanagement.controller;

import com.rajesh.hospital.hospitalmanagement.dto.AppointmentResponseDto;
import com.rajesh.hospital.hospitalmanagement.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;
import com.rajesh.hospital.hospitalmanagement.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.rajesh.hospital.hospitalmanagement.entity.Appointment;
import com.rajesh.hospital.hospitalmanagement.entity.Doctor;
import com.rajesh.hospital.hospitalmanagement.repository.AppointmentRepository;
import com.rajesh.hospital.hospitalmanagement.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final AppointmentService appointmentService;

    
    public DoctorController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointmentsOfDoctor() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(appointmentService.getAllAppointmentsOfDoctor(user.getId()));
    }

}
