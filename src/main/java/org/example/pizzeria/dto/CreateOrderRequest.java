package org.example.pizzeria.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateOrderRequest {
    private List<OrderItemRequest> items;
    private String customerName;
    private String customerPhone;
    private String deliveryAddress;
}

