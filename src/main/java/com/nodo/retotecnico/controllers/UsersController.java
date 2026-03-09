package com.nodo.retotecnico.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nodo.retotecnico.Models.Users;
import com.nodo.retotecnico.Services.UsersService;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;


    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }


    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(usersService.getAllUsers());
    }


    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        Users newUser = usersService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
