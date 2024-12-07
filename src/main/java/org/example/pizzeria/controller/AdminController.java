package org.example.pizzeria.controller;

import org.example.pizzeria.dto.UpdateOrderStatusRequest;
import org.example.pizzeria.entity.Order;
import org.example.pizzeria.entity.Pizza;
import org.example.pizzeria.repository.OrderRepository;
import org.example.pizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/pizzas")
    public Pizza createPizza(@RequestBody Pizza pizza) {
        return pizzaService.createPizza(pizza);
    }

    @PutMapping("/pizzas/{id}")
    public Pizza updatePizza(@PathVariable Long id, @RequestBody Pizza pizza) {
        return pizzaService.updatePizza(id, pizza);
    }

    @DeleteMapping("/pizzas/{id}")
    public void deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);
    }

    @PutMapping("/orders/{id}/status")
    public Order updateOrderStatus(@PathVariable Long id, @RequestBody UpdateOrderStatusRequest req) {
        Order o = orderRepository.findById(id).orElse(null);
        if (o != null) {
            o.setStatus(req.getStatus());
            orderRepository.save(o);
        }
        return o;
    }
}
