package com.rajesh.hospital.hospitalmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajesh.hospital.hospitalmanagement.dto.LoginRequestDto;
import com.rajesh.hospital.hospitalmanagement.dto.LoginResponseDTO;
import com.rajesh.hospital.hospitalmanagement.dto.RefreshTokenRequestDto;
import com.rajesh.hospital.hospitalmanagement.dto.SignupResponseDTO;
import com.rajesh.hospital.hospitalmanagement.security.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private  AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDto dto){
		
		return ResponseEntity.ok(authService.login(dto));
				
				
		
	}
	
	@PostMapping("/Signup")
	public ResponseEntity<SignupResponseDTO> signup(@RequestBody LoginRequestDto signupRequestDto){
		
		return ResponseEntity.ok(authService.signup(signupRequestDto));
				
				
		
	}
	
	 // 🔥 REFRESH TOKEN ENDPOINT
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(@RequestBody RefreshTokenRequestDto request) {
        return ResponseEntity.ok(authService.refresh(request.getRefreshToken()));
    }
	
	

}
