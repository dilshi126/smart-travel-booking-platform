package com.smarttravel.bookingservice.dto;

import java.math.BigDecimal;

public class FlightAvailabilityResponse {
    private boolean available;
    private BigDecimal price;
    private Long flightId;

    public FlightAvailabilityResponse() {}

    public FlightAvailabilityResponse(boolean available, BigDecimal price, Long flightId) {
        this.available = available;
        this.price = price;
        this.flightId = flightId;
    }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }
}
