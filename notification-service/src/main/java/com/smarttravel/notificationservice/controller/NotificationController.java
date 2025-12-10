package com.smarttravel.notificationservice.controller;

import com.smarttravel.notificationservice.dto.NotificationDTO;
import com.smarttravel.notificationservice.dto.NotificationRequest;
import com.smarttravel.notificationservice.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotificationsByUserId(userId));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByBookingId(@PathVariable Long bookingId) {
        return ResponseEntity.ok(notificationService.getNotificationsByBookingId(bookingId));
    }

    @PostMapping("/send")
    public ResponseEntity<NotificationDTO> sendNotification(@RequestBody NotificationRequest request) {
        return new ResponseEntity<>(notificationService.sendNotification(request), HttpStatus.CREATED);
    }
}
