package com.rajesh.hospital.hospitalmanagement.security;

import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rajesh.hospital.hospitalmanagement.entity.User;
import com.rajesh.hospital.hospitalmanagement.repository.*;
import com.rajesh.hospital.hospitalmanagement.security.*;


import ch.qos.logback.classic.Logger;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger log =
            (Logger) LoggerFactory.getLogger(JwtAuthFilter.class);

    private final UserRepository userRepository;
    private final AuthUtil authUtil;

    public JwtAuthFilter(UserRepository userRepository, AuthUtil authUtil) {
        this.userRepository = userRepository;
        this.authUtil = authUtil;
    }
    
    @Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
	    return request.getRequestURI().equals("/auth/refresh");
	}

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // 1️⃣ No token → skip JWT processing
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);

        try {
            // 2️⃣ Validate token & extract username and also verification
            String username = authUtil.getUserNameFromToken(token);

            // 3️⃣ Set authentication only if not already set
            if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

                User user = userRepository.findByUserName(username)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.debug("JWT authentication successful for user: {}", username);
            }

            filterChain.doFilter(request, response);

        }
        catch (io.jsonwebtoken.ExpiredJwtException ex) {
            // 🔥 TOKEN EXPIRED → UI SHOULD CALL REFRESH
            log.warn("JWT token expired");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                {
                  "error": "TOKEN_EXPIRED",
                  "message": "Access token expired"
                }
                """);
        }
        catch (Exception ex) {
            // ❌ INVALID TOKEN
            log.warn("Invalid JWT token: {}", ex.getMessage());

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                {
                  "error": "INVALID_TOKEN",
                  "message": "Invalid access token"
                }
                """);
        }
    }
}
