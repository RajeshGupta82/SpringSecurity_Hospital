package com.rajesh.hospital.hospitalmanagement.dto;

public class LoginResponseDTO {
	
	private String jwt;
	
    private String refreshToken;

    private long id;
    
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public void setId(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}
	public LoginResponseDTO(String jwt,String refreshToken ,long id) {
		super();
		this.jwt = jwt;
		this.id = id;
		this.refreshToken= refreshToken;
	}
	public LoginResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
