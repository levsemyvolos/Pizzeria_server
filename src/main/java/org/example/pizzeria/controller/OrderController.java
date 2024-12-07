package org.example.pizzeria.controller;

import org.example.pizzeria.dto.CreateOrderRequest;
import org.example.pizzeria.entity.Order;
import org.example.pizzeria.entity.User;
import org.example.pizzeria.repository.OrderRepository;
import org.example.pizzeria.repository.UserRepository;
import org.example.pizzeria.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping
    public Order createOrder(@RequestBody CreateOrderRequest req, Principal principal) {
        User user = principal != null ? userRepository.findByEmail(principal.getName()).orElse(null) : null;
        return orderService.createOrder(req, user);
    }

    @GetMapping("/my")
    public List<Order> getMyOrders(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElse(null);
        return orderRepository.findByUserId(user.getId());
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id, Principal principal) {
        Order o = orderRepository.findById(id).orElse(null);
        // Check access rights if needed
        return o;
    }
}