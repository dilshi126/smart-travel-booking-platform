package com.smarttravel.flightservice.controller;

import com.smarttravel.flightservice.dto.FlightDTO;
import com.smarttravel.flightservice.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<Map<String, Object>> checkAvailability(@PathVariable Long id) {
        boolean available = flightService.checkAvailability(id);
        FlightDTO flight = flightService.getFlightById(id);
        return ResponseEntity.ok(Map.of(
                "available", available,
                "price", flight.getPrice(),
                "flightId", id
        ));
    }

    @PostMapping("/{id}/book")
    public ResponseEntity<Map<String, String>> bookSeat(@PathVariable Long id) {
        flightService.decrementSeats(id);
        return ResponseEntity.ok(Map.of("message", "Seat booked successfully"));
    }

    @PostMapping
    public ResponseEntity<FlightDTO> createFlight(@RequestBody FlightDTO flightDTO) {
        return new ResponseEntity<>(flightService.createFlight(flightDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightDTO> updateFlight(@PathVariable Long id, @RequestBody FlightDTO flightDTO) {
        return ResponseEntity.ok(flightService.updateFlight(id, flightDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }
}
