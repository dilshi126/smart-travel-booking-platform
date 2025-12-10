package com.smarttravel.bookingservice.client;

import com.smarttravel.bookingservice.dto.HotelAvailabilityResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "hotel-service", url = "${service.hotel.url}")
public interface HotelClient {

    @GetMapping("/api/hotels/{id}/availability")
    HotelAvailabilityResponse checkAvailability(@PathVariable("id") Long hotelId);

    @PostMapping("/api/hotels/{id}/book")
    void bookHotel(@PathVariable("id") Long hotelId);
}
