package org.example.pizzeria.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String role;
}
