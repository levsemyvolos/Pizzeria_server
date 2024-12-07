package org.example.pizzeria.controller;

import org.example.pizzeria.dto.AuthRequest;
import org.example.pizzeria.dto.AuthResponse;
import org.example.pizzeria.dto.RegisterRequest;
import org.example.pizzeria.entity.User;
import org.example.pizzeria.service.AuthService;
import org.example.pizzeria.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest req) {
        User u = authService.registerUser(req);
        AuthResponse resp = new AuthResponse();
        resp.setToken(jwtUtil.generateToken(u.getEmail(), u.getRole()));
        resp.setRole(u.getRole());
        return resp;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) {
        User u = authService.validateUser(req.getEmail(), req.getPassword());
        if (u == null) {
            throw new RuntimeException("Invalid credentials");
        }
        AuthResponse resp = new AuthResponse();
        resp.setToken(jwtUtil.generateToken(u.getEmail(), u.getRole()));
        resp.setRole(u.getRole());
        return resp;
    }
}
