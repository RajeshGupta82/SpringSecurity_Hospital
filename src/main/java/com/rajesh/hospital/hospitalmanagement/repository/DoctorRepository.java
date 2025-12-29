package com.rajesh.hospital.hospitalmanagement.repository;

import com.rajesh.hospital.hospitalmanagement.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}