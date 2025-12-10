package com.smarttravel.flightservice.config;

import com.smarttravel.flightservice.entity.Flight;
import com.smarttravel.flightservice.repository.FlightRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    private final FlightRepository flightRepository;

    public DataInitializer(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public void run(String... args) {
        flightRepository.save(new Flight(null, "SL100", "SriLankan Airlines", "Colombo", "Singapore",
                LocalDateTime.of(2025, 1, 10, 8, 0), LocalDateTime.of(2025, 1, 10, 14, 0),
                new BigDecimal("350.00"), 50));
        flightRepository.save(new Flight(null, "SL200", "SriLankan Airlines", "Colombo", "Dubai",
                LocalDateTime.of(2025, 1, 10, 10, 0), LocalDateTime.of(2025, 1, 10, 14, 30),
                new BigDecimal("420.00"), 30));
        flightRepository.save(new Flight(null, "EK500", "Emirates", "Dubai", "London",
                LocalDateTime.of(2025, 1, 11, 6, 0), LocalDateTime.of(2025, 1, 11, 12, 0),
                new BigDecimal("650.00"), 100));
    }
}
