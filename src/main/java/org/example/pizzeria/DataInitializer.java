package org.example.pizzeria;

import org.example.pizzeria.entity.Pizza;
import org.example.pizzeria.entity.User;
import org.example.pizzeria.repository.PizzaRepository;
import org.example.pizzeria.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer {

    @Bean
    CommandLineRunner loadData(PizzaRepository pizzaRepository, UserRepository userRepository) {
        return args -> {
            // Створення тестових піц
            pizzaRepository.saveAll(Arrays.asList(
                    new Pizza(null, "Pepperoni", "Classic pepperoni pizza with cheese", 8.99, null, Arrays.asList("25cm", "30cm"), Arrays.asList("thin", "traditional")),
                    new Pizza(null, "Margarita", "Tomato sauce, mozzarella, and basil", 7.99, null, Arrays.asList("25cm", "30cm", "35cm"), Arrays.asList("thin")),
                    new Pizza(null, "Hawaiian", "Ham and pineapple on a cheese base", 9.49, null, Arrays.asList("30cm", "35cm"), Arrays.asList("traditional"))
            ));

            // Створення тестових користувачів
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userRepository.saveAll(Arrays.asList(
                    new User(null, "admin@pizzeria.com", encoder.encode("password"), "Admin", "1234567890", "123 Main St", "ADMIN"),
                    new User(null, "customer@pizzeria.com", encoder.encode("password"), "Customer", "0987654321", "456 Other St", "CUSTOMER")
            ));
        };
    }
}
