package com.rajesh.hospital.hospitalmanagement.entity;

import com.rajesh.hospital.hospitalmanagement.entity.type.BloodGroupType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.rajesh.hospital.hospitalmanagement.entity.User;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Index;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Id;


@Entity
@Table(
        name = "patient",
        uniqueConstraints = {
//                @UniqueConstraint(name = "unique_patient_email", columnNames = {"email"}),
                @UniqueConstraint(name = "unique_patient_name_birthdate", columnNames = {"name", "birthDate"})
        },
        indexes = {
                @Index(name = "idx_patient_birth_date", columnList = "birthDate")
        }
)
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String name;

    //    @ToString.Exclude
    private LocalDate birthDate;

    @Column(unique = true, nullable = false)
    private String email;

    private String gender;

	// this will create one-to-one mapping between Patient and User entities and no separate PK for Patient
	@OneToOne
	@MapsId
	private User user;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private BloodGroupType bloodGroup;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "patient_insurance_id") // owning side
    private Insurance insurance;

    @OneToMany(mappedBy = "patient", cascade = {CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Appointment> appointments = new ArrayList<>();

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

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public BloodGroupType getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(BloodGroupType bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public Insurance getInsurance() {
		return insurance;
	}

	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Patient(Long id, String name, LocalDate birthDate, String email, String gender, LocalDateTime createdAt,
			BloodGroupType bloodGroup, Insurance insurance, List<Appointment> appointments) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.email = email;
		this.gender = gender;
		this.createdAt = createdAt;
		this.bloodGroup = bloodGroup;
		this.insurance = insurance;
		this.appointments = appointments;
	}

	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User getUser() {
		return user;
	}	

	public void setUser(User user) {
		this.user = user;
	}


    
    
    
    
}