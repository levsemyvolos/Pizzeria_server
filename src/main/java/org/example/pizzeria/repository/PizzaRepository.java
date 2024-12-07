package org.example.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.pizzeria.entity.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
}
