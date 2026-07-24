package com.nodo.retotecnico.controllers;

import com.nodo.retotecnico.models.BetaTester;
import com.nodo.retotecnico.models.Users;
import com.nodo.retotecnico.models.AuthProvider;
import com.nodo.retotecnico.repositories.BetaTesterRepository;
import com.nodo.retotecnico.repositories.UsersRepository;
import com.nodo.retotecnico.dto.*;
import com.nodo.retotecnico.security.JwtUtils;
import com.nodo.retotecnico.security.TokenRevocationService;
import com.nodo.retotecnico.services.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsersRepository usersRepository;
    private final BetaTesterRepository betaTesterRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final TokenRevocationService tokenRevocationService;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (usersRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El email ya está registrado");
        }

        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCountry(request.getCountry());
        user.setIdentification(request.getIdentification());
        user.setFullName(request.getFullName());
        user.setMobileNumber(request.getMobileNumber());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setDateOfAdmission(LocalDate.now()); 
        user.setProvider(AuthProvider.FORM); 

        usersRepository.save(user);
        emailService.sendWelcomeEmail(user.getEmail(), user.getFullName(), "USER");
        return ResponseEntity.ok("Usuario creado con éxito");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body(new ErrorResponse("Credenciales inválidas"));
        }

        String token = jwtUtils.generateToken(request.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(new ErrorResponse("Token ausente o con formato invalido"));
        }

        String token = authHeader.substring(7);
        if (!jwtUtils.validateToken(token)) {
            return ResponseEntity.status(401).body(new ErrorResponse("Token invalido o expirado"));
        }

        tokenRevocationService.revokeToken(token);
        return ResponseEntity.ok(Map.of("message", "Sesion cerrada con exito"));
    }

    @PostMapping("/beta/register")
    public ResponseEntity<?> registerBeta(@RequestBody RegisterRequest request) {
        if (betaTesterRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("El email ya está registrado como beta tester"));
        }

        BetaTester betaTester = new BetaTester();
        betaTester.setEmail(request.getEmail());
        betaTester.setPassword(passwordEncoder.encode(request.getPassword()));
        betaTester.setCountry(request.getCountry());
        betaTester.setIdentification(request.getIdentification());
        betaTester.setFullName(request.getFullName());
        betaTester.setMobileNumber(request.getMobileNumber());
        betaTester.setDateOfBirth(request.getDateOfBirth());
        betaTester.setDateOfAdmission(LocalDate.now());
        betaTester.setProvider(AuthProvider.FORM);

        betaTesterRepository.save(betaTester);
        emailService.sendWelcomeEmail(betaTester.getEmail(), betaTester.getFullName(), "BETA");
        return ResponseEntity.ok("Beta tester creado con éxito");
    }

    @PostMapping("/beta/login")
    public ResponseEntity<?> loginBeta(@RequestBody LoginRequest request) {
        BetaTester betaTester = betaTesterRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (betaTester == null || !passwordEncoder.matches(request.getPassword(), betaTester.getPassword())) {
            return ResponseEntity.status(401).body(new ErrorResponse("Credenciales inválidas"));
        }

        String token = jwtUtils.generateToken(request.getEmail(), "BETA");
        return ResponseEntity.ok(new AuthResponse(token));
    }

    public record ErrorResponse(String message) {}
}