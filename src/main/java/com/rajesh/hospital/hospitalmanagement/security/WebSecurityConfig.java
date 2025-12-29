package com.rajesh.hospital.hospitalmanagement.security;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpServletRequest;


@Configuration
public class WebSecurityConfig {
	
	
	
    private static final Logger log =
            (Logger) LoggerFactory.getLogger(WebSecurityConfig.class);
	private final  PasswordEncoder passwordEncoder;

	private final JwtAuthFilter jwtAuthFilter;
	
	public WebSecurityConfig(PasswordEncoder passwordEncoder, JwtAuthFilter jwtAuthFilter) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.jwtAuthFilter = jwtAuthFilter;
	}
	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
	
        log.info("Initializing Spring Security Filter Chain");

	    httpSecurity
	        .csrf(csrf -> csrf.disable()) // ❌ disable CSRF
	        .sessionManagement(session -> 
	            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 🔥 no session stored
	        )
	        .authorizeHttpRequests(authz -> authz
	            .requestMatchers("/public/**", "/auth/**").permitAll()
	           // .requestMatchers("/admin/**").hasRole("ADMIN")
	            //.requestMatchers("/pateint/**").hasAnyRole("ADMIN","DOCTOR")
	            .anyRequest().authenticated()
	        )
	        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
	        .formLogin(form -> form.disable()); // ❌ disable form login (required for JWT)
	        // OR:
	        // .formLogin(Customizer.withDefaults()); // if you still want default form login

	    return httpSecurity.build();
	}

	
	

	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		UserDetails user1 = User.withUsername("admin")
//				.password(passwordEncoder.encode("pass"))
//				.roles("ADMIN")
//				.build();
//		
//		
//		UserDetails user2 = User.withUsername("patient")
//				.password(passwordEncoder.encode("pass"))
//				.roles("PATIENT")
//				.build();
//		
//		
//		UserDetails user3 = User.withUsername("doctor")
//				.password(passwordEncoder.encode("pass"))
//				.roles("DOCTOR")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user1,user2,user3);
//				
//	}

}
