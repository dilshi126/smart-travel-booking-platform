package com.smarttravel.bookingservice.service;

import com.smarttravel.bookingservice.client.FlightClient;
import com.smarttravel.bookingservice.client.HotelClient;
import com.smarttravel.bookingservice.dto.*;
import com.smarttravel.bookingservice.entity.Booking;
import com.smarttravel.bookingservice.exception.BookingException;
import com.smarttravel.bookingservice.exception.ResourceNotFoundException;
import com.smarttravel.bookingservice.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);
    private final BookingRepository bookingRepository;
    private final FlightClient flightClient;
    private final HotelClient hotelClient;
    private final WebClient userWebClient;
    private final WebClient paymentWebClient;
    private final WebClient notificationWebClient;

    public BookingService(BookingRepository bookingRepository, FlightClient flightClient, HotelClient hotelClient,
                          WebClient userWebClient, WebClient paymentWebClient, WebClient notificationWebClient) {
        this.bookingRepository = bookingRepository;
        this.flightClient = flightClient;
        this.hotelClient = hotelClient;
        this.userWebClient = userWebClient;
        this.paymentWebClient = paymentWebClient;
        this.notificationWebClient = notificationWebClient;
    }

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        return toDTO(booking);
    }

    public List<BookingDTO> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public BookingDTO createBooking(BookingRequest request) {
        log.info("Creating booking for user: {}", request.getUserId());

        // 1. Validate user via WebClient
        validateUser(request.getUserId());

        // 2. Check flight availability via Feign Client
        FlightAvailabilityResponse flightResponse = flightClient.checkAvailability(request.getFlightId());
        if (!flightResponse.isAvailable()) {
            throw new BookingException("Flight is not available");
        }
        log.info("Flight {} is available with price: {}", request.getFlightId(), flightResponse.getPrice());

        // 3. Check hotel availability via Feign Client
        HotelAvailabilityResponse hotelResponse = hotelClient.checkAvailability(request.getHotelId());
        if (!hotelResponse.isAvailable()) {
            throw new BookingException("Hotel is not available");
        }
        log.info("Hotel {} is available with price: {}", request.getHotelId(), hotelResponse.getPricePerNight());

        // 4. Calculate total cost
        BigDecimal totalCost = flightResponse.getPrice().add(hotelResponse.getPricePerNight());

        // 5. Create booking with PENDING status
        Booking booking = new Booking();
        booking.setUserId(request.getUserId());
        booking.setFlightId(request.getFlightId());
        booking.setHotelId(request.getHotelId());
        booking.setTravelDate(request.getTravelDate());
        booking.setTotalCost(totalCost);
        booking.setStatus(Booking.BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());

        Booking saved = bookingRepository.save(booking);
        log.info("Booking created with id: {} and status: PENDING", saved.getId());

        // 6. Book flight and hotel
        flightClient.bookFlight(request.getFlightId());
        hotelClient.bookHotel(request.getHotelId());

        // 7. Process payment via WebClient
        processPayment(saved.getId(), totalCost);

        // 8. Send notification via WebClient
        sendNotification(request.getUserId(), saved.getId(), totalCost);

        // 9. Update booking to CONFIRMED
        saved.setStatus(Booking.BookingStatus.CONFIRMED);
        saved.setUpdatedAt(LocalDateTime.now());
        saved = bookingRepository.save(saved);
        log.info("Booking {} confirmed successfully", saved.getId());

        return toDTO(saved);
    }

    private void validateUser(Long userId) {
        try {
            UserValidationResponse response = userWebClient.get()
                    .uri("/api/users/{id}/validate", userId)
                    .retrieve()
                    .bodyToMono(UserValidationResponse.class)
                    .block();

            if (response == null || !response.isValid()) {
                throw new BookingException("User validation failed for userId: " + userId);
            }
            log.info("User {} validated successfully", userId);
        } catch (Exception e) {
            log.error("User validation failed: {}", e.getMessage());
            throw new BookingException("User validation failed: " + e.getMessage());
        }
    }

    private void processPayment(Long bookingId, BigDecimal amount) {
        try {
            PaymentRequest paymentRequest = new PaymentRequest(bookingId, amount, "CREDIT_CARD");
            paymentWebClient.post()
                    .uri("/api/payments/process")
                    .bodyValue(paymentRequest)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
            log.info("Payment processed for booking: {}", bookingId);
        } catch (Exception e) {
            log.error("Payment processing failed: {}", e.getMessage());
            throw new BookingException("Payment processing failed: " + e.getMessage());
        }
    }

    private void sendNotification(Long userId, Long bookingId, BigDecimal totalCost) {
        try {
            NotificationRequest notificationRequest = new NotificationRequest(
                    userId, bookingId, "EMAIL", "user@example.com",
                    "Booking Confirmation",
                    "Your booking #" + bookingId + " has been confirmed. Total cost: $" + totalCost
            );
            notificationWebClient.post()
                    .uri("/api/notifications/send")
                    .bodyValue(notificationRequest)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
            log.info("Notification sent for booking: {}", bookingId);
        } catch (Exception e) {
            log.error("Notification sending failed: {}", e.getMessage());
        }
    }

    public BookingDTO confirmBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        booking.setUpdatedAt(LocalDateTime.now());
        return toDTO(bookingRepository.save(booking));
    }

    public BookingDTO cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        booking.setStatus(Booking.BookingStatus.CANCELLED);
        booking.setUpdatedAt(LocalDateTime.now());
        return toDTO(bookingRepository.save(booking));
    }

    private BookingDTO toDTO(Booking booking) {
        return new BookingDTO(booking.getId(), booking.getUserId(), booking.getFlightId(),
                booking.getHotelId(), booking.getTravelDate(), booking.getTotalCost(),
                booking.getStatus().name(), booking.getCreatedAt(), booking.getUpdatedAt());
    }
}
