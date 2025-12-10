package com.smarttravel.flightservice.service;

import com.smarttravel.flightservice.dto.FlightDTO;
import com.smarttravel.flightservice.entity.Flight;
import com.smarttravel.flightservice.exception.ResourceNotFoundException;
import com.smarttravel.flightservice.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public FlightDTO getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
        return toDTO(flight);
    }

    public FlightDTO createFlight(FlightDTO dto) {
        Flight flight = toEntity(dto);
        return toDTO(flightRepository.save(flight));
    }

    public FlightDTO updateFlight(Long id, FlightDTO dto) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
        flight.setFlightNumber(dto.getFlightNumber());
        flight.setAirline(dto.getAirline());
        flight.setOrigin(dto.getOrigin());
        flight.setDestination(dto.getDestination());
        flight.setDepartureTime(dto.getDepartureTime());
        flight.setArrivalTime(dto.getArrivalTime());
        flight.setPrice(dto.getPrice());
        flight.setAvailableSeats(dto.getAvailableSeats());
        return toDTO(flightRepository.save(flight));
    }

    public void deleteFlight(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new ResourceNotFoundException("Flight not found with id: " + id);
        }
        flightRepository.deleteById(id);
    }

    public boolean checkAvailability(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
        return flight.getAvailableSeats() > 0;
    }

    public void decrementSeats(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
        if (flight.getAvailableSeats() > 0) {
            flight.setAvailableSeats(flight.getAvailableSeats() - 1);
            flightRepository.save(flight);
        }
    }

    private FlightDTO toDTO(Flight flight) {
        return new FlightDTO(flight.getId(), flight.getFlightNumber(), flight.getAirline(),
                flight.getOrigin(), flight.getDestination(), flight.getDepartureTime(),
                flight.getArrivalTime(), flight.getPrice(), flight.getAvailableSeats());
    }

    private Flight toEntity(FlightDTO dto) {
        return new Flight(dto.getId(), dto.getFlightNumber(), dto.getAirline(),
                dto.getOrigin(), dto.getDestination(), dto.getDepartureTime(),
                dto.getArrivalTime(), dto.getPrice(), dto.getAvailableSeats());
    }
}
