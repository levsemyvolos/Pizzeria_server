package org.example.pizzeria.service;

import org.example.pizzeria.entity.Pizza;
import org.example.pizzeria.repository.OrderItemRepository;
import org.example.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Pizza createPizza(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public void updatePizza(Long id, Pizza updated) {
        Pizza p = pizzaRepository.findById(id).orElseThrow(() -> new RuntimeException("Pizza not found"));
        p.setName(updated.getName());
        p.setDescription(updated.getDescription());
        p.setBasePrice(updated.getBasePrice());
        p.setAvailableSizes(updated.getAvailableSizes());
        p.setDoughTypes(updated.getDoughTypes());
        p.setImageUrl(updated.getImageUrl());
        pizzaRepository.save(p);
    }

    public void deletePizza(Long id) {
        if (!pizzaRepository.existsById(id)) {
            throw new RuntimeException("Pizza not found");
        }
        if (orderItemRepository.existsByPizzaId(id)) {
            throw new RuntimeException("Cannot delete pizza because it is associated with existing orders");
        }
        pizzaRepository.deleteById(id);
    }

}