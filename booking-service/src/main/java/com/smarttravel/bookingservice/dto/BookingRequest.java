package com.smarttravel.bookingservice.dto;

import java.time.LocalDate;

public class BookingRequest {
    private Long userId;
    private Long flightId;
    private Long hotelId;
    private LocalDate travelDate;

    public BookingRequest() {}

    public BookingRequest(Long userId, Long flightId, Long hotelId, LocalDate travelDate) {
        this.userId = userId;
        this.flightId = flightId;
        this.hotelId = hotelId;
        this.travelDate = travelDate;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }
    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
    public LocalDate getTravelDate() { return travelDate; }
    public void setTravelDate(LocalDate travelDate) { this.travelDate = travelDate; }
}
