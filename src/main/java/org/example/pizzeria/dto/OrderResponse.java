package org.example.pizzeria.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class OrderResponse {
    private Long orderId;
    private Date orderDate;
    private String status;
    private String customerName;
    private String customerPhone;
    private String deliveryAddress;
    private double totalPrice;
    private List<OrderItemResponse> items;
}

