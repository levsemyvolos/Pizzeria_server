package org.example.pizzeria.controller;

import org.example.pizzeria.dto.ErrorResponse;
import org.example.pizzeria.dto.MessageResponse;
import org.example.pizzeria.dto.UpdateOrderStatusRequest;
import org.example.pizzeria.dto.PizzaResponse;
import org.example.pizzeria.dto.PizzaRequest;
import org.example.pizzeria.entity.User;
import org.example.pizzeria.repository.UserRepository;
import org.example.pizzeria.service.OrderService;
import org.example.pizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/pizzas")
    public ResponseEntity<?> createPizza(@RequestBody PizzaRequest pizzaRequest, Authentication auth) {
        User u = userRepository.findByEmail(auth.getName()).orElse(null);
        if (u == null || !"ADMIN".equals(u.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("Access denied"));
        }

        try {
            PizzaResponse created = pizzaService.createPizza(pizzaRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new MessageResponse("Pizza created successfully with ID " + created.getId()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @PutMapping("/pizzas/{id}")
    public ResponseEntity<?> updatePizza(@PathVariable Long id, @RequestBody PizzaRequest pizzaRequest, Authentication auth) {
        User u = userRepository.findByEmail(auth.getName()).orElse(null);
        if (u == null || !"ADMIN".equals(u.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("Access denied"));
        }

        try {
            pizzaService.updatePizza(id, pizzaRequest);
            return ResponseEntity.ok(new MessageResponse("Pizza updated successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/pizzas/{id}")
    public ResponseEntity<?> deletePizza(@PathVariable Long id, Authentication auth) {
        User u = userRepository.findByEmail(auth.getName()).orElse(null);
        if (u == null || !"ADMIN".equals(u.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("Access denied"));
        }

        try {
            pizzaService.deletePizza(id);
            return ResponseEntity.ok(new MessageResponse("Pizza deleted"));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Cannot delete")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(e.getMessage()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @PutMapping("/orders/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestBody UpdateOrderStatusRequest req, Authentication auth) {
        User u = userRepository.findByEmail(auth.getName()).orElse(null);
        if (u == null || !"ADMIN".equals(u.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("Access denied"));
        }

        try {
            orderService.updateOrderStatus(id, req.getStatus(), u);
            return ResponseEntity.ok(new MessageResponse("Order status updated"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }
}