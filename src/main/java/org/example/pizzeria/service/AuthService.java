package org.example.pizzeria.service;

import org.example.pizzeria.dto.RegisterRequest;
import org.example.pizzeria.entity.User;
import org.example.pizzeria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(RegisterRequest req) {
        User user = new User();
        user.setEmail(req.getEmail());
        user.setPasswordHash(BCrypt.hashpw(req.getPassword(), BCrypt.gensalt()));
        user.setName(req.getName());
        user.setPhone(req.getPhone());
        user.setAddress(req.getAddress());
        user.setRole("CUSTOMER");
        return userRepository.save(user);
    }

    public User validateUser(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(u -> BCrypt.checkpw(password, u.getPasswordHash()))
                .orElse(null);
    }
}
