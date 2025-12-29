package com.rajesh.hospital.hospitalmanagement.error;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Apierror> handleUserNotFoundException(UsernameNotFoundException ex){ 
		Apierror apr= new Apierror(ex.getMessage(), HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Apierror>(apr, apr.getStatuscode());
		
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Apierror> handleException(Exception ex){
	Apierror apr= new Apierror("An unexpected error occured "+ex.getMessage(), HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<Apierror>(apr, HttpStatus.OK);
		
	}

	
	

}
