package com.smarttravel.notificationservice.dto;

import java.time.LocalDateTime;

public class NotificationDTO {
    private Long id;
    private Long userId;
    private Long bookingId;
    private String type;
    private String recipient;
    private String subject;
    private String message;
    private String status;
    private LocalDateTime sentAt;

    public NotificationDTO() {}

    public NotificationDTO(Long id, Long userId, Long bookingId, String type, String recipient,
                           String subject, String message, String status, LocalDateTime sentAt) {
        this.id = id;
        this.userId = userId;
        this.bookingId = bookingId;
        this.type = type;
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.status = status;
        this.sentAt = sentAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
}
