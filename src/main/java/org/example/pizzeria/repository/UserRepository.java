package org.example.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.pizzeria.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
