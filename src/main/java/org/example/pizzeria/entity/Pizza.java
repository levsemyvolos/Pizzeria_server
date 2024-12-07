package org.example.pizzeria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pizza {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length=2000)
    private String description;
    private double basePrice;
    private String imageUrl; // For simplicity

    // For availableSizes and doughTypes, we could store as JSON or separate tables
    @ElementCollection
    private List<String> availableSizes = new ArrayList<>();

    @ElementCollection
    private List<String> doughTypes = new ArrayList<>();
}
