package com.rajesh.hospital.hospitalmanagement.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.rajesh.hospital.hospitalmanagement.dto.LoginResponseDTO;


import java.io.IOException;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {


    private final AuthService authService;

    public OAuth2SuccessHandler(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        // ✔ authentication itself is OAuth2AuthenticationToken
        OAuth2AuthenticationToken oauthToken =
                (OAuth2AuthenticationToken) authentication;

        // ✔ principal is OAuth2User
        OAuth2User oauth2User = oauthToken.getPrincipal();


        // registration id (google, facebook, github)
        String registrationId = oauthToken.getAuthorizedClientRegistrationId();
   

        LoginResponseDTO loginResponse = authService.handleOauth2LoginRequest(oauth2User, registrationId);

        response.setContentType("application/json");
        response.getWriter().write(
                "{ \"accessToken\": \"" + loginResponse.getJwt() + "\", " +
                "\"refreshToken\": \"" + loginResponse.getRefreshToken() + "\", " +
                "\"id\": \"" + loginResponse.getId() + "\" }"
        );
        response.setStatus(HttpServletResponse.SC_OK);


        // Example: create or update user
      //  authUtil.processOAuthPostLogin(email, name, registrationId);

        
    }


//     public class OAuth2AuthenticationProvider
//         implements AuthenticationProvider {

//     @Override
//     public Authentication authenticate(Authentication authentication) {

//         // 1️⃣ Exchange authorization code for access token
//         // 2️⃣ Call Google UserInfo endpoint
//         // 3️⃣ Build OAuth2User
//         // 4️⃣ Create AUTHENTICATED token

//         return new OAuth2AuthenticationToken(
//                 oauth2User,
//                 authorities,
//                 clientRegistrationId
//         ); // 🔥 authenticated = true
//     }
// }

}
