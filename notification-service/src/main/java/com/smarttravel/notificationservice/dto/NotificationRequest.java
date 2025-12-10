package com.smarttravel.notificationservice.dto;

public class NotificationRequest {
    private Long userId;
    private Long bookingId;
    private String type;
    private String recipient;
    private String subject;
    private String message;

    public NotificationRequest() {}

    public NotificationRequest(Long userId, Long bookingId, String type, String recipient, String subject, String message) {
        this.userId = userId;
        this.bookingId = bookingId;
        this.type = type;
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
    }

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
}
