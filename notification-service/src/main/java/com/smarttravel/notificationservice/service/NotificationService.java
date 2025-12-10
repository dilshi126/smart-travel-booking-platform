package com.smarttravel.notificationservice.service;

import com.smarttravel.notificationservice.dto.NotificationDTO;
import com.smarttravel.notificationservice.dto.NotificationRequest;
import com.smarttravel.notificationservice.entity.Notification;
import com.smarttravel.notificationservice.exception.ResourceNotFoundException;
import com.smarttravel.notificationservice.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public NotificationDTO getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        return toDTO(notification);
    }

    public List<NotificationDTO> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public List<NotificationDTO> getNotificationsByBookingId(Long bookingId) {
        return notificationRepository.findByBookingId(bookingId).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    public NotificationDTO sendNotification(NotificationRequest request) {
        log.info("Sending {} notification to user {} for booking {}",
                request.getType(), request.getUserId(), request.getBookingId());

        Notification notification = new Notification();
        notification.setUserId(request.getUserId());
        notification.setBookingId(request.getBookingId());
        notification.setType(Notification.NotificationType.valueOf(request.getType().toUpperCase()));
        notification.setRecipient(request.getRecipient());
        notification.setSubject(request.getSubject());
        notification.setMessage(request.getMessage());
        notification.setSentAt(LocalDateTime.now());
        notification.setStatus(Notification.NotificationStatus.SENT);
        
        Notification saved = notificationRepository.save(notification);
        log.info("Notification sent successfully with id: {}", saved.getId());
        
        return toDTO(saved);
    }

    private NotificationDTO toDTO(Notification notification) {
        return new NotificationDTO(notification.getId(), notification.getUserId(),
                notification.getBookingId(), notification.getType().name(),
                notification.getRecipient(), notification.getSubject(),
                notification.getMessage(), notification.getStatus().name(),
                notification.getSentAt());
    }
}
