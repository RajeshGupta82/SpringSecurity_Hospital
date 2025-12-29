package com.rajesh.hospital.hospitalmanagement.service;

import com.rajesh.hospital.hospitalmanagement.entity.Insurance;
import com.rajesh.hospital.hospitalmanagement.entity.Patient;
import com.rajesh.hospital.hospitalmanagement.repository.InsuranceRepository;
import com.rajesh.hospital.hospitalmanagement.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsuranceService {
	@Autowired
    private  InsuranceRepository insuranceRepository;
	@Autowired
    private  PatientRepository patientRepository;

    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));

        patient.setInsurance(insurance);
        insurance.setPatient(patient); // bidirectional consistency maintainence

        return patient;
    }

    @Transactional
    public Patient disaccociateInsuranceFromPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));

        patient.setInsurance(null);
        return patient;
    }

    // HW
    //Create three appointment for a patient and then delete Patient


}
