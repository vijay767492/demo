package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        // Checks if the username is already taken
        if (userService.findUserByUsername(user.getUsername()) != null) {
            return "Username already taken!";
        }
        userService.registerUser(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        // Finds user by username
        User user = userService.findUserByUsername(username);
        // Verifies if the password matches the hashed password in the database
        if (user != null && userService.getPasswordEncoder().matches(password, user.getPassword())) {
            return "Login successful! Redirecting to dashboard...";
        } else {
            return "Invalid username or password.";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Welcome to your dashboard!";
    }
}
