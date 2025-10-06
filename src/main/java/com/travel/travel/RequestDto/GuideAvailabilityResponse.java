package com.travel.travel.RequestDto;

import lombok.Data;

@Data
public class GuideAvailabilityResponse {
    private boolean available;
    private String message;
    
    public GuideAvailabilityResponse(boolean available, String message) {
        this.available = available;
        this.message = message;
    }
}