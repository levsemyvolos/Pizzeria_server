package org.example.pizzeria.service;

import org.example.pizzeria.dto.UserResponse;
import org.example.pizzeria.entity.User;
import org.example.pizzeria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse getCurrentUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        dto.setRole(user.getRole());

        return dto;
    }

    public User updateProfile(User user, String name, String phone, String address) {
        user.setName(name);
        user.setPhone(phone);
        user.setAddress(address);
        return userRepository.save(user);
    }

    public User changePassword(User user, String newPassword) {
        user.setPasswordHash(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        return userRepository.save(user);
    }
}
