package com.rajesh.hospital.hospitalmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class DoctorResponseDto {
    private Long id;
    private String name;
    private String specialization;
    private String email;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public DoctorResponseDto(Long id, String name, String specialization, String email) {
		super();
		this.id = id;
		this.name = name;
		this.specialization = specialization;
		this.email = email;
	}
	public DoctorResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
}
