package com.rajesh.hospital.hospitalmanagement.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class Apierror {
	
	private LocalDateTime timestamp;
	
	private String message ;
	
	private HttpStatus statuscode;
	
	public Apierror() {
		this.timestamp= LocalDateTime.now();
	}
	
	public Apierror(String message,HttpStatus statuscode ) {
		this();
		this.message=message;
		this.statuscode=statuscode;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(HttpStatus statuscode) {
		this.statuscode = statuscode;
	}
	
	
	

}
