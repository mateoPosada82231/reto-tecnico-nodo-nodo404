package com.nodo.retotecnico.controllers;

import com.nodo.retotecnico.Models.Users;
import com.nodo.retotecnico.Models.AuthProvider;
import com.nodo.retotecnico.Repositories.UsersRepository;
import com.nodo.retotecnico.dto.*;
import com.nodo.retotecnico.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

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
        return ResponseEntity.ok("Usuario creado con éxito");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = jwtUtils.generateToken(request.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}