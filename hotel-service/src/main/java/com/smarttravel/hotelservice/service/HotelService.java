package com.smarttravel.hotelservice.service;

import com.smarttravel.hotelservice.dto.HotelDTO;
import com.smarttravel.hotelservice.entity.Hotel;
import com.smarttravel.hotelservice.exception.ResourceNotFoundException;
import com.smarttravel.hotelservice.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<HotelDTO> getAllHotels() {
        return hotelRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public HotelDTO getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
        return toDTO(hotel);
    }

    public HotelDTO createHotel(HotelDTO dto) {
        Hotel hotel = toEntity(dto);
        return toDTO(hotelRepository.save(hotel));
    }

    public HotelDTO updateHotel(Long id, HotelDTO dto) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
        hotel.setName(dto.getName());
        hotel.setLocation(dto.getLocation());
        hotel.setAddress(dto.getAddress());
        hotel.setStarRating(dto.getStarRating());
        hotel.setPricePerNight(dto.getPricePerNight());
        hotel.setAvailableRooms(dto.getAvailableRooms());
        return toDTO(hotelRepository.save(hotel));
    }

    public void deleteHotel(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hotel not found with id: " + id);
        }
        hotelRepository.deleteById(id);
    }

    public boolean checkAvailability(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
        return hotel.getAvailableRooms() > 0;
    }

    public void decrementRooms(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
        if (hotel.getAvailableRooms() > 0) {
            hotel.setAvailableRooms(hotel.getAvailableRooms() - 1);
            hotelRepository.save(hotel);
        }
    }

    private HotelDTO toDTO(Hotel hotel) {
        return new HotelDTO(hotel.getId(), hotel.getName(), hotel.getLocation(),
                hotel.getAddress(), hotel.getStarRating(), hotel.getPricePerNight(), hotel.getAvailableRooms());
    }

    private Hotel toEntity(HotelDTO dto) {
        return new Hotel(dto.getId(), dto.getName(), dto.getLocation(),
                dto.getAddress(), dto.getStarRating(), dto.getPricePerNight(), dto.getAvailableRooms());
    }
}
