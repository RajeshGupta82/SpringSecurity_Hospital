package com.rajesh.hospital.hospitalmanagement.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.rajesh.hospital.hospitalmanagement.dto.LoginRequestDto;
import com.rajesh.hospital.hospitalmanagement.dto.LoginResponseDTO;
import com.rajesh.hospital.hospitalmanagement.dto.SignupResponseDTO;
import com.rajesh.hospital.hospitalmanagement.entity.RefreshToken;
import com.rajesh.hospital.hospitalmanagement.entity.User;
import com.rajesh.hospital.hospitalmanagement.repository.RefreshTokenRepository;
import com.rajesh.hospital.hospitalmanagement.repository.UserRepository;
import com.rajesh.hospital.hospitalmanagement.entity.type.AuthProviderType;
import com.rajesh.hospital.hospitalmanagement.entity.type.Roletype;
import com.rajesh.hospital.hospitalmanagement.dto.SignUpRequestDto;
import com.rajesh.hospital.hospitalmanagement.repository.PatientRepository;
import java.util.Set;
import com.rajesh.hospital.hospitalmanagement.entity.Patient;






@Service
public class AuthService {
	
	private final AuthenticationManager authenticationManager;
	
	private final AuthUtil authUtil;
	
	private final UserRepository userRepository;
	
	
    private final PasswordEncoder passwordEncoder;
    
    private final RefreshTokenRepository refreshTokenRepository;

	private final PatientRepository patientRepository;

	
	
	

	public AuthService(AuthenticationManager authenticationManager, AuthUtil authUtil , UserRepository userRepository,PasswordEncoder passwordEncoder,RefreshTokenRepository refreshTokenRepository, PatientRepository patientRepository) {
		super();
		this.authenticationManager = authenticationManager;
		this.authUtil = authUtil;
		this.userRepository= userRepository;
		
		this.passwordEncoder =passwordEncoder;
		this.refreshTokenRepository=refreshTokenRepository;
		this.patientRepository = patientRepository;
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



	public User SignupInternal(SignUpRequestDto signupRequestDto) {
	    // Check if user exists
	    User existingUser = userRepository.findByUserName(signupRequestDto.getUserName())
	            .orElse(null);
	    if (existingUser != null) {
	        throw new IllegalArgumentException("User already exists");
	    }

	    // Encode password
	    String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());

	    // Save user with encoded password
	    User user = new User(signupRequestDto.getUserName(), encodedPassword, signupRequestDto.getRoles());
	    user = userRepository.save(user);
		Patient pateint = new Patient();
	pateint.setName(signupRequestDto.getName());
	pateint.setEmail(signupRequestDto.getUserName());
	pateint.setUser(user);
	patientRepository.save(pateint);

	    // Return user
	    return user;
	}
	




	public User signupInternal(
        SignUpRequestDto signupRequestDto,
        String providerId,
        AuthProviderType authProviderType
) {
    User existingUser = userRepository
            .findByUserName(signupRequestDto.getUserName())
            .orElse(null);

    if (existingUser != null) {
        throw new IllegalArgumentException("User already exists");
    }

    String encodedPassword = signupRequestDto.getPassword() != null
            ? passwordEncoder.encode(signupRequestDto.getPassword())
            : null;

    User user = new User(signupRequestDto.getUserName(), encodedPassword);
    user.setProviderId(providerId);
    user.setAuthProviderType(authProviderType);
	user.setRoles(Set.of(Roletype.PATIENT)); // default role

	user = userRepository.save(user);

	Patient pateint = new Patient();
	pateint.setName(signupRequestDto.getName());
	pateint.setEmail(signupRequestDto.getUserName());
	pateint.setUser(user);
	patientRepository.save(pateint);
    return user;
}


	public SignupResponseDTO signup(SignUpRequestDto signupRequestDto) {

	    User user = SignupInternal(signupRequestDto);

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
	
	public LoginResponseDTO handleOauth2LoginRequest(OAuth2User oauth2User, String registrationId) {
		// TODO Auto-generated method stub
		// fetch provider type , provider id from oauth2User object
		//save the info with the user so that next time we can identify the user was logged in using which provider
		// if user has the account direct login else create 
		//else signup the user and login

 

    AuthProviderType providerType =
            authUtil.getAuthProviderTypeFromRegistrationId(registrationId);

    String providerId =
            authUtil.getProviderIdfromOAuth2User(oauth2User, registrationId);

    // 1️⃣ Find by provider
    User user = userRepository
            .findByProviderIdAndAuthProviderType(providerId, providerType)
            .orElse(null);

    String email = oauth2User.getAttribute("email");

	String name = oauth2User.getAttribute("name");

    // 2️⃣ If user not found, check email conflict
    if (user == null && email != null) {
        User emailUser = userRepository.findByUserName(email).orElse(null);
        if (emailUser != null) {
            throw new IllegalArgumentException(
                "Account exists with different login method: " + email
            );
        }
    }

    // 3️⃣ Signup if new
   if (user == null) {
    String username =
            authUtil.determineUserNameFromOAuth2User(oauth2User, registrationId);

    SignUpRequestDto signUpRequest = new SignUpRequestDto();
    signUpRequest.setUserName(username);
    signUpRequest.setPassword(null); // OAuth users usually don't have local password
    signUpRequest.setName(name);
    signUpRequest.setRoles(Set.of(Roletype.PATIENT));

    user = signupInternal(
            signUpRequest,
            providerId,
            providerType
    );

    System.out.println("OAuth user signed up: " + user.getUserName());
}

	else {
		System.out.println("OAuth user logged in: " + user.getUserName());
	}
    // 4️⃣ Issue JWTs
    String accessToken = authUtil.generateAccesssToken(user);
    String refreshToken = authUtil.generateRefreshToken(user);

    return new LoginResponseDTO(accessToken, refreshToken, user.getId());
}

		
		



	
	

}
