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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;


import ch.qos.logback.classic.Logger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;





@Configuration
public class WebSecurityConfig {
	
	
	
    private static final Logger log =
            (Logger) LoggerFactory.getLogger(WebSecurityConfig.class);
	private final  PasswordEncoder passwordEncoder;

	private final JwtAuthFilter jwtAuthFilter;

	private final OAuth2SuccessHandler oAuth2SuccessHandler;
	
	public WebSecurityConfig(PasswordEncoder passwordEncoder, JwtAuthFilter jwtAuthFilter, OAuth2SuccessHandler oAuth2SuccessHandler) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.jwtAuthFilter = jwtAuthFilter;
		this.oAuth2SuccessHandler = oAuth2SuccessHandler;
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
	        .requestMatchers("/admin/**").hasRole("ADMIN")
	           .requestMatchers("/patient/**").hasAnyRole("ADMIN","DOCTOR")
	            .anyRequest().authenticated()
	        )
	        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			.oauth2Login(oauth2 -> oauth2.failureHandler((HttpServletRequest request, HttpServletResponse response,
			            AuthenticationException exception) -> {
							log.error("OAuth2 Login failed: {}", exception.getMessage());
							//response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "OAuth2 Login Failed");
     
			}
			
			).successHandler(oAuth2SuccessHandler))
			 .exceptionHandling(ex -> ex
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    log.error("Access Denied: {}", accessDeniedException.getMessage());
                    response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "You do not have permission to access this resource"
                    );
                })
            )
	
	        
			
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
