package org.example.pizzeria.controller;

import org.example.pizzeria.dto.CreateOrderRequest;
import org.example.pizzeria.dto.OrderResponse;
import org.example.pizzeria.entity.User;
import org.example.pizzeria.repository.UserRepository;
import org.example.pizzeria.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired private OrderService orderService;
    @Autowired private UserRepository userRepository;

    @PostMapping
    public OrderResponse createOrder(@RequestBody CreateOrderRequest req, Authentication auth) {
        User user = auth != null ? userRepository.findByEmail(auth.getName()).orElse(null) : null;
        return orderService.createOrder(req, user);
    }

    @GetMapping("/my")
    public List<OrderResponse> getMyOrders(Authentication auth) {
        User u = userRepository.findByEmail(auth.getName()).orElseThrow();
        return orderService.getMyOrders(u);
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id, Authentication auth) {
        User u = userRepository.findByEmail(auth.getName()).orElseThrow();
        return orderService.getOrderById(id, u);
    }

    @PutMapping("/{id}/cancel")
    public OrderResponse cancelOrder(@PathVariable Long id, Authentication auth) {
        User u = userRepository.findByEmail(auth.getName()).orElseThrow();
        return orderService.cancelOrder(id, u);
    }
}