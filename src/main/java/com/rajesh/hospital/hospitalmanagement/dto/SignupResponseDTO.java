package com.rajesh.hospital.hospitalmanagement.dto;

public class SignupResponseDTO {
	
	private Long id;
	
	private String userName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public SignupResponseDTO(Long id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
	}

	public SignupResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
