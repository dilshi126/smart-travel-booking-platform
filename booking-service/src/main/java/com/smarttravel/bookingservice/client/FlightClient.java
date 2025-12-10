package com.smarttravel.bookingservice.client;

import com.smarttravel.bookingservice.dto.FlightAvailabilityResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "flight-service", url = "${service.flight.url}")
public interface FlightClient {

    @GetMapping("/api/flights/{id}/availability")
    FlightAvailabilityResponse checkAvailability(@PathVariable("id") Long flightId);

    @PostMapping("/api/flights/{id}/book")
    void bookFlight(@PathVariable("id") Long flightId);
}
