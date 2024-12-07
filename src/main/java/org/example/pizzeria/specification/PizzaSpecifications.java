package org.example.pizzeria.specification;

import org.example.pizzeria.entity.Pizza;
import org.springframework.data.jpa.domain.Specification;

public class PizzaSpecifications {

    public static Specification<Pizza> hasNameLike(String search) {
        return (root, query, builder) ->
                builder.like(builder.lower(root.get("name")), "%" + search.toLowerCase() + "%");
    }

    public static Specification<Pizza> hasSize(String size) {
        return (root, query, builder) ->
                builder.isMember(size, root.get("availableSizes"));
    }

    public static Specification<Pizza> hasDoughType(String dough) {
        return (root, query, builder) ->
                builder.isMember(dough, root.get("doughTypes"));
    }
}
