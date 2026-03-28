package com.nodo.retotecnico.security.config;

import com.nodo.retotecnico.security.JwtAuthFilter;
import com.nodo.retotecnico.security.OAuth2SuccessHandler;
import com.nodo.retotecnico.security.OAuth2UserServiceImpl;
import com.nodo.retotecnico.security.UserDetailsServiceImpl;
import com.nodo.retotecnico.security.handlers.JsonAccessDeniedHandler;
import com.nodo.retotecnico.security.handlers.JsonAuthenticationEntryPoint;
import com.nodo.retotecnico.security.handlers.JsonErrorWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsServiceImpl userDetailsService;

    private final JsonAuthenticationEntryPoint authenticationEntryPoint;
    private final JsonAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private OAuth2UserServiceImpl oAuth2UserService;

    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register", "/api/auth/login", "/oauth2/**", "/login/**", "/error").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/extensions/**").permitAll()
                        .requestMatchers("/api/auth/logout").authenticated()
                        .requestMatchers("/api/cart/**", "/api/buys/**").authenticated()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService)
                        )
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                            Map<String, Object> body = new HashMap<>();
                            body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
                            body.put("error", "Unauthorized");
                            body.put("message", exception.getMessage());
                            body.put("path", request.getRequestURI());
                            body.put("timestamp", Instant.now().toString());

                            response.getWriter().write(JsonErrorWriter.toJson(body));
                        })
                        .successHandler(oAuth2SuccessHandler)
                )

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
