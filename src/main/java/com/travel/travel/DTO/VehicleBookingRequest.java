package com.travel.travel.DTO;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class VehicleBookingRequest {
    
    private Long vehicleId;
    private Long userId;
    
    // Booking Details
    private LocalDateTime pickupDate;
    private String pickupTime;
    private LocalDateTime returnDate;
    private String returnTime;
    private String pickupLocation;
    private String dropoffLocation;
    
    // Driver Options
    private Boolean withDriver;
    private String driverLicenseNumber;
    private LocalDate licenseExpiryDate;
    
    // Pricing
    private BigDecimal basePrice;
    private BigDecimal driverFee;
    private BigDecimal totalCost;
    private Integer numberOfDays;
    
    // Payment Information
    private String paymentIntentId;
    private String paymentStatus;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    
    // Booking Status
    private String bookingStatus;
    
    // Contact Information
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    
    // Additional Information
    private String specialRequests;
}
