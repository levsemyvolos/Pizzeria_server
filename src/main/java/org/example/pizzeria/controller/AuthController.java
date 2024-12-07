package org.example.pizzeria.controller;

import org.example.pizzeria.dto.*;
import org.example.pizzeria.entity.User;
import org.example.pizzeria.service.AuthService;
import org.example.pizzeria.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        try {
            User u = authService.registerUser(req);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new RegisterResponse("User registered successfully", u.getId()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        User u = authService.validateUser(req.getEmail(), req.getPassword());
        if (u == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid credentials"));
        }
        String token = jwtUtil.generateToken(u.getEmail(), u.getRole());
        return ResponseEntity.ok(new AuthResponse(token, u.getRole()));
    }
}
