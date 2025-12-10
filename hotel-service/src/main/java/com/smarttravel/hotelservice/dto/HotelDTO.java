package com.smarttravel.hotelservice.dto;

import java.math.BigDecimal;

public class HotelDTO {
    private Long id;
    private String name;
    private String location;
    private String address;
    private Integer starRating;
    private BigDecimal pricePerNight;
    private Integer availableRooms;

    public HotelDTO() {}

    public HotelDTO(Long id, String name, String location, String address, Integer starRating,
                    BigDecimal pricePerNight, Integer availableRooms) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.address = address;
        this.starRating = starRating;
        this.pricePerNight = pricePerNight;
        this.availableRooms = availableRooms;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Integer getStarRating() { return starRating; }
    public void setStarRating(Integer starRating) { this.starRating = starRating; }
    public BigDecimal getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(BigDecimal pricePerNight) { this.pricePerNight = pricePerNight; }
    public Integer getAvailableRooms() { return availableRooms; }
    public void setAvailableRooms(Integer availableRooms) { this.availableRooms = availableRooms; }
}
