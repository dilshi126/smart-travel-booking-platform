package com.smarttravel.bookingservice.dto;

public class UserValidationResponse {
    private boolean valid;

    public UserValidationResponse() {}

    public UserValidationResponse(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }
}
