package org.example.pizzeria.controller;

import org.example.pizzeria.dto.*;
import org.example.pizzeria.entity.User;
import org.example.pizzeria.repository.UserRepository;
import org.example.pizzeria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private UserRepository userRepository;

    @Autowired private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("User not authenticated"));
        }

        try {
            UserResponse userResponse = userService.getCurrentUserProfile(auth.getName());
            return ResponseEntity.ok(userResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest req, Authentication auth) {
        User u = userRepository.findByEmail(auth.getName()).orElse(null);
        if (u == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("User not found"));
        }
        userService.updateProfile(u, req.getName(), req.getPhone(), req.getAddress());
        return ResponseEntity.ok(new MessageResponse("Profile updated successfully"));
    }

    @PutMapping("/me/password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest req, Authentication auth) {
        User u = userRepository.findByEmail(auth.getName()).orElse(null);
        if (u == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("User not found"));
        }

        if (!BCrypt.checkpw(req.getOldPassword(), u.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Invalid old password"));
        }
        userService.changePassword(u, req.getNewPassword());
        return ResponseEntity.ok(new MessageResponse("Password changed successfully"));
    }
}
