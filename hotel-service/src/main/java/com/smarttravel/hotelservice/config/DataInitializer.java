package com.smarttravel.hotelservice.config;

import com.smarttravel.hotelservice.entity.Hotel;
import com.smarttravel.hotelservice.repository.HotelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    private final HotelRepository hotelRepository;

    public DataInitializer(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public void run(String... args) {
        hotelRepository.save(new Hotel(null, "Cinnamon Grand", "Colombo", "77 Galle Road, Colombo 03",
                5, new BigDecimal("150.00"), 20));
        hotelRepository.save(new Hotel(null, "Shangri-La", "Colombo", "1 Galle Face, Colombo 02",
                5, new BigDecimal("200.00"), 15));
        hotelRepository.save(new Hotel(null, "Hilton Colombo", "Colombo", "2 Sir Chittampalam A Gardiner Mawatha",
                5, new BigDecimal("180.00"), 25));
    }
}
