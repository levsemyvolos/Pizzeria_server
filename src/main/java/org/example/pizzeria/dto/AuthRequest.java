package org.example.pizzeria.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
