package com.travel.travel.DTOs;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.Data;

/**
 * DTO for creating a new guide booking request
 * Accepts IDs instead of full entities to simplify frontend integration
 * Supports multi-guide booking by accepting a list of guide IDs
 */
@Data
public class GuideBookingRequest {
    private Long guideId; // Primary guide ID (for single guide or first in list)
    private List<Long> guideIds; // All selected guide IDs (for multi-guide requests)
    private Long userId;
    private String locations;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private Integer numberOfDays;
    private Integer numberOfPeople;
    private String contactNumber;
    private String specialRequests;
    private String preferredLanguage;
    private Boolean accommodationNeeded;
    private Boolean transportationNeeded;
    private String mealPreferences;
    private Double totalPrice;
    private String multiRequestId; // For multi-guide bookings (auto-generated if not provided)
}
