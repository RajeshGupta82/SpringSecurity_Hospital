package com.rajesh.hospital.hospitalmanagement.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rajesh.hospital.hospitalmanagement.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import com.rajesh.hospital.hospitalmanagement.entity.type.AuthProviderType;
import org.springframework.security.oauth2.core.user.OAuth2User;


@Component
public class AuthUtil {
	
	@Value("${jwt.secretkey}")
	private String jwtSecretKey;
	
	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
	}
	
	public String generateAccesssToken(User user) {
		
		 return Jwts.builder()
		            .setSubject(user.getUsername()) // main id of the token
		            .claim("userId", user.getId()) // optional custom field
		            .setIssuedAt(new Date())
		            .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 hour
		            .signWith(getSecretKey())
		            .compact();
		
		
	}
	
	public String generateRefreshToken(User user) {

	    return Jwts.builder()
	            .setSubject(user.getUsername())
	            .setIssuedAt(new Date())
	            .setExpiration(
	                    new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000) // 7 days
	            )
	            .signWith(getSecretKey())
	            .compact();
	}


	public String getUserNameFromToken(String token) {

	    // Step 1: Create a JWT parser
	    // This parser will be responsible for validating and reading the token
	    Claims claims = Jwts.parserBuilder()

	            // Step 2: Provide the SECRET KEY used while signing the token
	            // JJWT will use this key to:
	            //   - Recalculate the signature (header + payload + secret)
	            //   - Compare it with the signature present in the token
	            // If the key is wrong → signature mismatch → exception thrown
	            .setSigningKey(getSecretKey())

	            // Step 3: Build the parser with the above configuration
	            .build()

	            // Step 4: Parse and VERIFY the token
	            // This single call does ALL of the following automatically:
	            //   1. Splits token into header.payload.signature
	            //   2. Recreates signature using the secret key
	            //   3. Compares recreated signature with token signature
	            //   4. Checks token expiration (exp claim)
	            //   5. Ensures token format is valid
	            // If any check fails → exception is thrown (token rejected)
	            .parseClaimsJws(token)

	            // Step 5: If verification succeeds, return the payload (claims)
	            .getBody();

	    // Step 6: Extract the subject from verified claims
	    // Subject was set during token creation:
	    //   .setSubject(user.getUsername())
	    // At this point, the username is TRUSTED and SAFE to use
	    return claims.getSubject();
	}


	public AuthProviderType getAuthProviderTypeFromRegistrationId(String registrationId) {
	    switch (registrationId.toLowerCase()) {
	        case "google":
	            return AuthProviderType.GOOGLE;
	        case "facebook":
	            return AuthProviderType.FACEBOOK;
	        case "github":
	            return AuthProviderType.GITHUB;
	        default:
	            throw new IllegalArgumentException("Unknown registration ID: " + registrationId);
	    }
	}

	public String getProviderIdfromOAuth2User(OAuth2User oauth2User, String registrationId) {
	    // Depending on the provider, the unique ID attribute may differ
	    // Here we handle common providers; you can extend this as needed
	   
	    switch (registrationId.toLowerCase()) {
	        case "google":
	            return oauth2User.getAttribute("sub"); // Google's unique ID
	        case "facebook":
	            return oauth2User.getAttribute("id"); // Facebook's unique ID
	        case "github":
	            return oauth2User.getAttribute("id").toString(); // GitHub's unique ID
	        default:
	            throw new IllegalArgumentException("Unknown registration ID: " + registrationId);
	    }
	}

	public String determineUserNameFromOAuth2User(OAuth2User oauth2User, String registrationId) {
		String email = oauth2User.getAttribute("email");
		if (email != null && !email.isEmpty()) {
			return email;
		}

		return switch (registrationId.toLowerCase()) {
			case "google" -> oauth2User.getAttribute("sub");
			case "facebook" -> oauth2User.getAttribute("name");
			case "github" -> oauth2User.getAttribute("login");
			default -> null;
		};

	
	}

	
	
	

}
