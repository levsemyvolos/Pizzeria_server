package org.example.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.pizzeria.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
