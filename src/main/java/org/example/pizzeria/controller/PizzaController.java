package org.example.pizzeria.controller;

import org.example.pizzeria.dto.PizzaResponse;
import org.example.pizzeria.dto.PizzaRequest;
import org.example.pizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    public Page<PizzaResponse> getAllPizzas(
            @RequestParam(required=false) String search,
            @RequestParam(required=false) String size,
            @RequestParam(required=false) String dough,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int sizePage,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return pizzaService.getAllPizzas(search, size, dough, page, sizePage, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public PizzaResponse getPizzaById(@PathVariable Long id) {
        return pizzaService.getPizzaById(id);
    }

    @PostMapping
    public PizzaResponse createPizza(@RequestBody PizzaRequest pizzaRequest) {
        return pizzaService.createPizza(pizzaRequest);
    }

    @PutMapping("/{id}")
    public PizzaResponse updatePizza(@PathVariable Long id, @RequestBody PizzaRequest pizzaRequest) {
        return pizzaService.updatePizza(id, pizzaRequest);
    }

    @DeleteMapping("/{id}")
    public void deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);
    }
}