package com.rajesh.hospital.hospitalmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.rajesh.hospital.hospitalmanagement.entity.User;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	// this will create one-to-one mapping between Patient and User entities and no separate PK for Patient
	@OneToOne
	@MapsId
	private User user;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String specialization;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @ManyToMany(mappedBy = "doctors")
    private Set<Department> departments = new HashSet<>();

    @OneToMany(mappedBy = "doctor")
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

	public Set<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Doctor(Long id, String name, String specialization, String email, Set<Department> departments,
			List<Appointment> appointments) {
		super();
		this.id = id;
		this.name = name;
		this.specialization = specialization;
		this.email = email;
		this.departments = departments;
		this.appointments = appointments;
	}

	public Doctor() {
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
