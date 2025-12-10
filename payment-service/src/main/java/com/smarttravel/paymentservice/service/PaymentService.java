package com.smarttravel.paymentservice.service;

import com.smarttravel.paymentservice.dto.PaymentDTO;
import com.smarttravel.paymentservice.dto.PaymentRequest;
import com.smarttravel.paymentservice.entity.Payment;
import com.smarttravel.paymentservice.exception.PaymentException;
import com.smarttravel.paymentservice.exception.ResourceNotFoundException;
import com.smarttravel.paymentservice.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentRepository paymentRepository;
    private final WebClient bookingWebClient;

    public PaymentService(PaymentRepository paymentRepository, WebClient bookingWebClient) {
        this.paymentRepository = paymentRepository;
        this.bookingWebClient = bookingWebClient;
    }

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PaymentDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        return toDTO(payment);
    }

    public PaymentDTO getPaymentByBookingId(Long bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for booking: " + bookingId));
        return toDTO(payment);
    }

    public PaymentDTO processPayment(PaymentRequest request) {
        log.info("Processing payment for booking: {}", request.getBookingId());
        
        Payment payment = new Payment();
        payment.setBookingId(request.getBookingId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(Payment.PaymentStatus.COMPLETED);
        
        Payment saved = paymentRepository.save(payment);
        log.info("Payment completed with transaction: {}", saved.getTransactionId());
        
        notifyBookingService(request.getBookingId());
        
        return toDTO(saved);
    }

    private void notifyBookingService(Long bookingId) {
        try {
            bookingWebClient.put()
                    .uri("/api/bookings/{id}/confirm", bookingId)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
            log.info("Booking {} confirmed successfully", bookingId);
        } catch (Exception e) {
            log.error("Failed to notify booking service: {}", e.getMessage());
        }
    }

    public PaymentDTO refundPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        
        if (payment.getStatus() != Payment.PaymentStatus.COMPLETED) {
            throw new PaymentException("Can only refund completed payments");
        }
        
        payment.setStatus(Payment.PaymentStatus.REFUNDED);
        return toDTO(paymentRepository.save(payment));
    }

    private PaymentDTO toDTO(Payment payment) {
        return new PaymentDTO(payment.getId(), payment.getBookingId(), payment.getAmount(),
                payment.getStatus().name(), payment.getPaymentMethod(),
                payment.getTransactionId(), payment.getPaymentDate());
    }
}
