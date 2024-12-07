package org.example.pizzeria.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name="pizza_id")
    private Pizza pizza;

    private int quantity;
    private String selectedSize;
    private String selectedDough;
    private double unitPrice;
    private double totalItemPrice;
}
