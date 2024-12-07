package org.example.pizzeria.dto;

import lombok.Data;
import java.util.List;

@Data
public class PizzaResponse {
    private Long id;
    private String name;
    private String description;
    private double basePrice;
    private String imageUrl;
    private List<String> availableSizes;
    private List<String> doughTypes;
}
