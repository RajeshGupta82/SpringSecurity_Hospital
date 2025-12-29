package com.rajesh.hospital.hospitalmanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentResponseDto {
    private Long id;
    
    
    
    private LocalDateTime appointmentTime;
    private String reason;
    private DoctorResponseDto doctor;
    
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getAppointmentTime() {
		return appointmentTime;
	}
	public void setAppointmentTime(LocalDateTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public DoctorResponseDto getDoctor() {
		return doctor;
	}
	public void setDoctor(DoctorResponseDto doctor) {
		this.doctor = doctor;
	}
	public AppointmentResponseDto(Long id, LocalDateTime appointmentTime, String reason, DoctorResponseDto doctor) {
		super();
		this.id = id;
		this.appointmentTime = appointmentTime;
		this.reason = reason;
		this.doctor = doctor;
	}
	public AppointmentResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
//    private PatientResponseDto patient;
}
