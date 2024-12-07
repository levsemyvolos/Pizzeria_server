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
            // Basic test pizzas (from previous example)
            pizzaRepository.saveAll(Arrays.asList(
                    new Pizza(null, "Pepperoni", "Classic pepperoni pizza with cheese", 8.99, "http://localhost:8080/images/pizzas/pepperoni.jpg",
                            Arrays.asList("25cm", "30cm"), Arrays.asList("thin", "traditional")),
                    new Pizza(null, "Margarita", "Tomato sauce, mozzarella, and basil", 7.99, "http://localhost:8080/images/pizzas/margarita.jpg",
                            Arrays.asList("25cm", "30cm", "35cm"), Arrays.asList("thin")),
                    new Pizza(null, "Hawaiian", "Ham and pineapple on a cheese base", 9.49, "http://localhost:8080/images/pizzas/hawaiian.jpg",
                            Arrays.asList("30cm", "35cm"), Arrays.asList("traditional"))
            ));

            // Add a larger variety of pizzas
            pizzaRepository.saveAll(Arrays.asList(
                    new Pizza(null, "Quattro Formaggi", "Four cheeses: mozzarella, gorgonzola, parmesan, and fontina", 10.99, "http://localhost:8080/images/pizzas/quattro.jpg",
                            Arrays.asList("25cm", "30cm"), Arrays.asList("thin", "traditional")),
                    new Pizza(null, "Marinara", "Tomato, garlic, oregano, extra virgin olive oil", 6.99, "http://localhost:8080/images/pizzas/marinara.jpg",
                            Arrays.asList("25cm", "30cm"), Arrays.asList("thin")),
                    new Pizza(null, "Prosciutto e Funghi", "Ham, mushrooms, tomato sauce, mozzarella", 9.99, "http://localhost:8080/images/pizzas/prosciutto_funghi.jpg",
                            Arrays.asList("30cm", "35cm"), Arrays.asList("thin", "traditional")),
                    new Pizza(null, "Diavola", "Spicy salami, tomato sauce, mozzarella", 10.49, "http://localhost:8080/images/pizzas/diavola.jpg",
                            Arrays.asList("25cm", "30cm", "35cm"), Arrays.asList("thin", "traditional")),
                    new Pizza(null, "Vegetariana", "Mushrooms, peppers, onions, zucchini, mozzarella", 9.89, "http://localhost:8080/images/pizzas/vegetariana.jpg",
                            Arrays.asList("25cm", "30cm"), Arrays.asList("thin", "traditional")),
                    new Pizza(null, "Frutti di Mare", "Seafood mix: shrimp, mussels, squid, tomato sauce, mozzarella", 12.49, "http://localhost:8080/images/pizzas/frutti_di_mare.jpg",
                            Arrays.asList("25cm", "30cm", "35cm"), Arrays.asList("thin")),
                    new Pizza(null, "Capricciosa", "Ham, mushrooms, artichokes, olives, tomato sauce, mozzarella", 10.79, "http://localhost:8080/images/pizzas/capricciosa.jpg",
                            Arrays.asList("25cm", "30cm"), Arrays.asList("thin", "traditional")),
                    new Pizza(null, "Calzone", "Folded pizza with ham, mozzarella, and tomato sauce", 9.99, "http://localhost:8080/images/pizzas/calzone.jpg",
                            Arrays.asList("30cm"), Arrays.asList("traditional")),
                    new Pizza(null, "Napoletana", "Anchovies, capers, olives, tomato sauce, mozzarella", 8.99, "http://localhost:8080/images/pizzas/napoletana.jpg",
                            Arrays.asList("25cm", "30cm"), Arrays.asList("thin")),
                    new Pizza(null, "Funghi", "Tomato sauce, mozzarella, mushrooms", 8.49, "http://localhost:8080/images/pizzas/funghi.jpg",
                            Arrays.asList("25cm", "30cm", "35cm"), Arrays.asList("thin", "traditional")),
                    new Pizza(null, "Siciliana", "Tomato sauce, mozzarella, eggplant, ricotta, basil", 10.29, "http://localhost:8080/images/pizzas/siciliana.jpg",
                            Arrays.asList("25cm", "30cm"), Arrays.asList("thin", "traditional")),
                    new Pizza(null, "Tonno e Cipolla", "Tuna, onion, tomato sauce, mozzarella", 10.49, "http://localhost:8080/images/pizzas/tonno_cipolla.jpg",
                            Arrays.asList("30cm", "35cm"), Arrays.asList("thin", "traditional")),
                    new Pizza(null, "Broccoli e Salsiccia", "Broccoli, sausage, mozzarella, garlic, olive oil", 11.49, "http://localhost:8080/images/pizzas/broccoli_salsiccia.jpg",
                            Arrays.asList("30cm"), Arrays.asList("thin", "traditional")),
                    new Pizza(null, "Quattro Stagioni", "Four sections: olives&artichokes, ham&mushrooms, seafood, peppers", 12.99, "http://localhost:8080/images/pizzas/quattro_stagioni.jpg",
                            Arrays.asList("30cm", "35cm"), Arrays.asList("traditional"))
            ));

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userRepository.saveAll(Arrays.asList(
                    new User(null, "admin@pizzeria.com", encoder.encode("password"), "Admin", "1234567890", "123 Main St", "ADMIN"),
                    new User(null, "customer@pizzeria.com", encoder.encode("password"), "Customer", "0987654321", "456 Other St", "CUSTOMER"),
                    new User(null, "john.doe@example.com", encoder.encode("secret123"), "John Doe", "+1234567890", "789 Another St", "CUSTOMER"),
                    new User(null, "foodie@example.com", encoder.encode("delicious"), "Foodie Lover", "+15005550006", "321 Food St", "CUSTOMER"),
                    new User(null, "manager@example.com", encoder.encode("managerpass"), "Manager", "+14005551234", "987 Manager Ave", "ADMIN")
            ));

            System.out.println("Data initialization completed. Database is now populated with pizzas and users.");
        };
    }
}