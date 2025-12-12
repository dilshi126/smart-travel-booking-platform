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
        userRepository.save(new User(null, "Dilshi Piyumika", "dilshi@gmail.com", "0771234567"));
        userRepository.save(new User(null, "Dasun Perera", "dasun@gmail.com", "0779876543"));
        userRepository.save(new User(null, "Nimali Fernando", "nimali@gmail.com", "0765555555"));
    }
}
