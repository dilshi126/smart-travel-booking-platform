package com.smarttravel.userservice.config;

import com.smarttravel.userservice.entity.User;
import com.smarttravel.userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        userRepository.save(new User(null, "John Doe", "john@example.com", "1234567890"));
        userRepository.save(new User(null, "Jane Smith", "jane@example.com", "0987654321"));
        userRepository.save(new User(null, "Bob Wilson", "bob@example.com", "5555555555"));
    }
}
