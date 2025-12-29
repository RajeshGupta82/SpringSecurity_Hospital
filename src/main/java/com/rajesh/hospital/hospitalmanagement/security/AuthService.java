package com.rajesh.hospital.hospitalmanagement.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rajesh.hospital.hospitalmanagement.dto.LoginRequestDto;
import com.rajesh.hospital.hospitalmanagement.dto.LoginResponseDTO;
import com.rajesh.hospital.hospitalmanagement.dto.SignupResponseDTO;
import com.rajesh.hospital.hospitalmanagement.entity.RefreshToken;
import com.rajesh.hospital.hospitalmanagement.entity.User;
import com.rajesh.hospital.hospitalmanagement.repository.RefreshTokenRepository;
import com.rajesh.hospital.hospitalmanagement.repository.UserRepository;

@Service
public class AuthService {
	
	private final AuthenticationManager authenticationManager;
	
	private final AuthUtil authUtil;
	
	private final UserRepository userRepository;
	
	
    private final PasswordEncoder passwordEncoder;
    
    private final RefreshTokenRepository refreshTokenRepository;

	
	
	

	public AuthService(AuthenticationManager authenticationManager, AuthUtil authUtil , UserRepository userRepository,PasswordEncoder passwordEncoder,RefreshTokenRepository refreshTokenRepository) {
		super();
		this.authenticationManager = authenticationManager;
		this.authUtil = authUtil;
		this.userRepository= userRepository;
		
		this.passwordEncoder =passwordEncoder;
		this.refreshTokenRepository=refreshTokenRepository;
	}





	public LoginResponseDTO login(LoginRequestDto dto) {
		// TODO Auto-generated method stub
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getUserName(), dto.getPassword()));
		
		User user = (User) authentication.getPrincipal();
		
		String accesstoken = authUtil.generateAccesssToken(user);
		
		String reftokan = authUtil.generateRefreshToken(user);
		
		
		RefreshToken storedToken = new RefreshToken();
		
		storedToken.setToken(reftokan);
	    storedToken.setUser(user);   // 🔥 IMPORTANT

	    storedToken.setExpiry(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000));
	    refreshTokenRepository.save(storedToken);
		
		return new LoginResponseDTO(accesstoken,reftokan,user.getId());
		
		
		
	
	
	}





	public SignupResponseDTO signup(LoginRequestDto signupRequestDto) {

	    // Check if user exists
	    User existingUser = userRepository.findByUserName(signupRequestDto.getUserName())
	            .orElse(null);
	    if (existingUser != null) {
	        throw new IllegalArgumentException("User already exists");
	    }

	    // Encode password
	    String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());

	    // Save user with encoded password
	    User user = new User(signupRequestDto.getUserName(), encodedPassword);
	    user = userRepository.save(user);

	    // Return response
	    return new SignupResponseDTO(Long.valueOf(user.getId()), user.getUserName());
	}
	
	
	public LoginResponseDTO refresh(String refreshToken) {

	    // 1. Verify refresh token signature & expiry
	    String username = authUtil.getUserNameFromToken(refreshToken);

	    // 2. Check token exists in DB
	    RefreshToken storedToken =
	            refreshTokenRepository.findByToken(refreshToken)
	                    .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

	    // 3. Load user
	    User user = userRepository.findByUserName(username)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    // 4. Generate new access token
	    String newAccessToken = authUtil.generateAccesssToken(user);

	    // 5. OPTIONAL: rotate refresh token
	    String newRefreshToken = authUtil.generateRefreshToken(user);

	    storedToken.setToken(newRefreshToken);
	    storedToken.setExpiry(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000));
	    refreshTokenRepository.save(storedToken);

	    return new LoginResponseDTO(newAccessToken, newRefreshToken, user.getId());
	}


	
	

}
