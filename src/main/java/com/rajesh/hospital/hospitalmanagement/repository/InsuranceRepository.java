package com.rajesh.hospital.hospitalmanagement.repository;

import com.rajesh.hospital.hospitalmanagement.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}