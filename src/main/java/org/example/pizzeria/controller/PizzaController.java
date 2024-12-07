package org.example.pizzeria.controller;

import org.example.pizzeria.entity.Pizza;
import org.example.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public List<Pizza> getAllPizzas(
            @RequestParam(required=false) String search,
            @RequestParam(required=false) String size,
            @RequestParam(required=false) String dough
    ) {
        if (search != null) {
            search = search.toLowerCase();
        }
        return pizzaRepository.filterPizzas(search, size, dough);
    }

    @GetMapping("/{id}")
    public Pizza getPizzaById(@PathVariable Long id) {
        return pizzaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza not found"));
    }
}
