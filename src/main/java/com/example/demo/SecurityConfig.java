package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoder bean
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF protection for testing
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/register", "/login").permitAll() // Allow public access
                .anyRequest().authenticated() // Require authentication for all other requests
            )
            .formLogin(form -> form
                .loginPage("/login") // Custom login page
                .defaultSuccessUrl("/dashboard", true) // Redirect to dashboard on success
                .failureUrl("/login?error=true") // Redirect to login with error on failure
                .permitAll() // Allow all access to login page
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout=true") // Redirect on logout
                .permitAll() // Allow all access to logout
            );

        return http.build(); // Build the security filter chain
    }
}
