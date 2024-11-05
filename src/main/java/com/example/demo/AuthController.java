package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        if (userService.findUserByUsername(user.getUsername()) != null) {
            return "Username already taken!"; // Handle duplicate username
        }
        userService.registerUser(user); // Register user
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        User user = userService.findUserByUsername(username); // Find user by username
        if (user != null && userService.passwordEncoder.matches(password, user.getPassword())) { // Check password
            return "Login successful! Redirecting to dashboard...";
        } else {
            return "Invalid username or password."; // Handle invalid credentials
        }
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Welcome to your dashboard!"; // Dashboard message
    }
}
