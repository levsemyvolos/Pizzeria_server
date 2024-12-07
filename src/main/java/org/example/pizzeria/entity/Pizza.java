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
    private String imageUrl;

    @ElementCollection
    @CollectionTable(name = "pizza_available_sizes", joinColumns = @JoinColumn(name = "pizza_id"))
    @Column(name = "size")
    private List<String> availableSizes = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "pizza_dough_types", joinColumns = @JoinColumn(name = "pizza_id"))
    @Column(name = "dough_type")
    private List<String> doughTypes = new ArrayList<>();
}
