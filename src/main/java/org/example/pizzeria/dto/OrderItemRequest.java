package org.example.pizzeria.dto;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long pizzaId;
    private int quantity;
    private String selectedSize;
    private String selectedDough;
}
