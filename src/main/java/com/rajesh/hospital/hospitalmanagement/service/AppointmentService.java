package com.rajesh.hospital.hospitalmanagement.service;

import com.rajesh.hospital.hospitalmanagement.dto.AppointmentResponseDto;
import com.rajesh.hospital.hospitalmanagement.dto.CreateAppointmentRequestDto;
import com.rajesh.hospital.hospitalmanagement.entity.Appointment;
import com.rajesh.hospital.hospitalmanagement.entity.Doctor;
import com.rajesh.hospital.hospitalmanagement.entity.Patient;
import com.rajesh.hospital.hospitalmanagement.repository.AppointmentRepository;
import com.rajesh.hospital.hospitalmanagement.repository.DoctorRepository;
import com.rajesh.hospital.hospitalmanagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
	@Autowired
    private  AppointmentRepository appointmentRepository;
	@Autowired
    private  DoctorRepository doctorRepository;
	@Autowired
    private  PatientRepository patientRepository;
	@Autowired
    private  ModelMapper modelMapper;

    @Transactional
    public AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto createAppointmentRequestDto) {
        Long doctorId = createAppointmentRequestDto.getDoctorId();
        Long patientId = createAppointmentRequestDto.getPatientId();

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId));
        Appointment appointment =new  Appointment();
        
        appointment.setReason(createAppointmentRequestDto.getReason());
        appointment.setAppointmentTime(createAppointmentRequestDto.getAppointmentTime());
                

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        patient.getAppointments().add(appointment); // to maintain consistency

        appointment = appointmentRepository.save(appointment);
        return modelMapper.map(appointment, AppointmentResponseDto.class);
    }

    @Transactional
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        appointment.setDoctor(doctor); // this will automatically call the update, because it is dirty

        doctor.getAppointments().add(appointment); // just for bidirectional consistency

        return appointment;
    }

    public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        return doctor.getAppointments()
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }
}
