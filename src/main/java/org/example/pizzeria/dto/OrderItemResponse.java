package org.example.pizzeria.dto;

import lombok.Data;

@Data
public class OrderItemResponse {
    private Long pizzaId;
    private int quantity;
    private String selectedSize;
    private String selectedDough;
    private double unitPrice;
    private double totalItemPrice;
}
