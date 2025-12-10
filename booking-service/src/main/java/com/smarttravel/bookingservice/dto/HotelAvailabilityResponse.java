package com.smarttravel.bookingservice.dto;

import java.math.BigDecimal;

public class HotelAvailabilityResponse {
    private boolean available;
    private BigDecimal pricePerNight;
    private Long hotelId;

    public HotelAvailabilityResponse() {}

    public HotelAvailabilityResponse(boolean available, BigDecimal pricePerNight, Long hotelId) {
        this.available = available;
        this.pricePerNight = pricePerNight;
        this.hotelId = hotelId;
    }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public BigDecimal getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(BigDecimal pricePerNight) { this.pricePerNight = pricePerNight; }
    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
}
