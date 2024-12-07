package org.example.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.pizzeria.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
