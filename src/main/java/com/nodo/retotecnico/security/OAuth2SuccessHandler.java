package com.nodo.retotecnico.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private static final String FRONTEND_URL = "http://localhost:5173";

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        OAuth2User user = (OAuth2User) authentication.getPrincipal();

        String email = user.getAttribute("email");

        String type = "USER";
        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
            String registrationId = oauthToken.getAuthorizedClientRegistrationId();
            if (registrationId != null && registrationId.endsWith("-beta")) {
                type = "BETA";
            }
        }

        String token = jwtUtils.generateToken(email, type);

        String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
        String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);
        String redirectUrl = FRONTEND_URL + "/oauth2/callback?token=" + encodedToken + "&email=" + encodedEmail;

        response.sendRedirect(redirectUrl);
    }
}
