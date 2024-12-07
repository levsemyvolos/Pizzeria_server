package org.example.pizzeria.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String address;
    private String role;
}

