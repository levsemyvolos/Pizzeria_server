package org.example.pizzeria.service;

import org.example.pizzeria.entity.Pizza;
import org.example.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    public Pizza getPizzaById(Long id) {
        return pizzaRepository.findById(id).orElse(null);
    }

    public Pizza createPizza(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public Pizza updatePizza(Long id, Pizza updated) {
        return pizzaRepository.findById(id).map(p -> {
            p.setName(updated.getName());
            p.setDescription(updated.getDescription());
            p.setBasePrice(updated.getBasePrice());
            p.setAvailableSizes(updated.getAvailableSizes());
            p.setDoughTypes(updated.getDoughTypes());
            p.setImageUrl(updated.getImageUrl());
            return pizzaRepository.save(p);
        }).orElse(null);
    }

    public boolean deletePizza(Long id) {
        if (pizzaRepository.existsById(id)) {
            pizzaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
