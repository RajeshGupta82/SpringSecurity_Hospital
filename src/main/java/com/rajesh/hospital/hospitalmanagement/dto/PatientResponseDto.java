package com.rajesh.hospital.hospitalmanagement.dto;

import com.rajesh.hospital.hospitalmanagement.entity.type.BloodGroupType;
import lombok.Data;

import java.time.LocalDate;


public class PatientResponseDto {
    private Long id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private BloodGroupType bloodGroup;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	public BloodGroupType getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(BloodGroupType bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public PatientResponseDto(Long id, String name, String gender, LocalDate birthDate, BloodGroupType bloodGroup) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.birthDate = birthDate;
		this.bloodGroup = bloodGroup;
	}
	public PatientResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
    
}
