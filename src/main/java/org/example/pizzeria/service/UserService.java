package org.example.pizzeria.service;

import org.example.pizzeria.entity.User;
import org.example.pizzeria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User updateProfile(User user, String name, String phone, String address) {
        user.setName(name);
        user.setPhone(phone);
        user.setAddress(address);
        return userRepository.save(user);
    }
}
