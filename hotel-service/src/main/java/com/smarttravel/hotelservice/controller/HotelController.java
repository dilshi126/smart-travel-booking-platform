package com.smarttravel.hotelservice.controller;

import com.smarttravel.hotelservice.dto.HotelDTO;
import com.smarttravel.hotelservice.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<Map<String, Object>> checkAvailability(@PathVariable Long id) {
        boolean available = hotelService.checkAvailability(id);
        HotelDTO hotel = hotelService.getHotelById(id);
        return ResponseEntity.ok(Map.of(
                "available", available,
                "pricePerNight", hotel.getPricePerNight(),
                "hotelId", id
        ));
    }

    @PostMapping("/{id}/book")
    public ResponseEntity<Map<String, String>> bookRoom(@PathVariable Long id) {
        hotelService.decrementRooms(id);
        return ResponseEntity.ok(Map.of("message", "Room booked successfully"));
    }

    @PostMapping
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelDTO hotelDTO) {
        return new ResponseEntity<>(hotelService.createHotel(hotelDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable Long id, @RequestBody HotelDTO hotelDTO) {
        return ResponseEntity.ok(hotelService.updateHotel(id, hotelDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }
}
