package org.example.pizzeria.controller;

import org.example.pizzeria.entity.Pizza;
import org.example.pizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    public List<Pizza> getAllPizzas() {
        return pizzaService.getAllPizzas();
    }

    @GetMapping("/{id}")
    public Pizza getPizzaById(@PathVariable Long id) {
        return pizzaService.getPizzaById(id);
    }
}
