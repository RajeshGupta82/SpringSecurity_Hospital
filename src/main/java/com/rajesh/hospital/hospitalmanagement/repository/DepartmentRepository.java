package com.rajesh.hospital.hospitalmanagement.repository;

import com.rajesh.hospital.hospitalmanagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}