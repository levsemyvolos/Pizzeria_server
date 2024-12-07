package org.example.pizzeria.service;

import org.example.pizzeria.dto.PizzaResponse;
import org.example.pizzeria.dto.PizzaRequest;
import org.example.pizzeria.entity.Pizza;
import org.example.pizzeria.repository.OrderItemRepository;
import org.example.pizzeria.repository.PizzaRepository;
import org.example.pizzeria.specification.PizzaSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Page<PizzaResponse> getAllPizzas(String search, String size, String dough, int page, int sizePage, String sortBy, String sortDir) {
        Specification<Pizza> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and(PizzaSpecifications.hasNameLike(search));
        }

        if (size != null && !size.isEmpty()) {
            spec = spec.and(PizzaSpecifications.hasSize(size));
        }

        if (dough != null && !dough.isEmpty()) {
            spec = spec.and(PizzaSpecifications.hasDoughType(dough));
        }

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, sizePage, sort);
        Page<Pizza> pizzaPage = pizzaRepository.findAll(spec, pageable);

        return pizzaPage.map(this::convertToDTO);
    }

    public PizzaResponse getPizzaById(Long id) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pizza not found"));
        return convertToDTO(pizza);
    }

    public PizzaResponse createPizza(PizzaRequest pizzaRequest) {
        Pizza pizza = convertToEntity(pizzaRequest);
        Pizza created = pizzaRepository.save(pizza);
        return convertToDTO(created);
    }

    public PizzaResponse updatePizza(Long id, PizzaRequest pizzaRequest) {
        Pizza existingPizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pizza not found"));

        existingPizza.setName(pizzaRequest.getName());
        existingPizza.setDescription(pizzaRequest.getDescription());
        existingPizza.setBasePrice(pizzaRequest.getBasePrice());
        existingPizza.setImageUrl(pizzaRequest.getImageUrl());
        existingPizza.setAvailableSizes(pizzaRequest.getAvailableSizes());
        existingPizza.setDoughTypes(pizzaRequest.getDoughTypes());

        Pizza savedPizza = pizzaRepository.save(existingPizza);
        return convertToDTO(savedPizza);
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

    private PizzaResponse convertToDTO(Pizza pizza) {
        PizzaResponse dto = new PizzaResponse();
        dto.setId(pizza.getId());
        dto.setName(pizza.getName());
        dto.setDescription(pizza.getDescription());
        dto.setBasePrice(pizza.getBasePrice());
        dto.setImageUrl(pizza.getImageUrl());
        dto.setAvailableSizes(pizza.getAvailableSizes());
        dto.setDoughTypes(pizza.getDoughTypes());
        return dto;
    }

    private Pizza convertToEntity(PizzaRequest dto) {
        Pizza pizza = new Pizza();
        pizza.setName(dto.getName());
        pizza.setDescription(dto.getDescription());
        pizza.setBasePrice(dto.getBasePrice());
        pizza.setImageUrl(dto.getImageUrl());
        pizza.setAvailableSizes(dto.getAvailableSizes());
        pizza.setDoughTypes(dto.getDoughTypes());
        return pizza;
    }
}