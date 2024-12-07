package org.example.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.pizzeria.entity.Pizza;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    @Query("SELECT p FROM Pizza p " +
            "WHERE (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:size IS NULL OR :size IN ELEMENTS(p.availableSizes)) " +
            "AND (:dough IS NULL OR :dough IN ELEMENTS(p.doughTypes))")
    List<Pizza> filterPizzas(@Param("search") String search,
                             @Param("size") String size,
                             @Param("dough") String dough);
}
