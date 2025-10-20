package com.travel.travel.Models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vehicle_bookings")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VehicleBooking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // References
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Vehicle vehicle;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;
    
    // Booking Details
    @Column(name = "pickup_date", nullable = false)
    private LocalDateTime pickupDate;
    
    @Column(name = "pickup_time", nullable = false)
    private LocalTime pickupTime;
    
    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;
    
    @Column(name = "return_time", nullable = false)
    private LocalTime returnTime;
    
    @Column(name = "pickup_location", nullable = false)
    private String pickupLocation;
    
    @Column(name = "dropoff_location", nullable = false)
    private String dropoffLocation;
    
    // Driver Options
    @Column(name = "with_driver", nullable = false)
    private Boolean withDriver = false;
    
    @Column(name = "driver_license_number")
    private String driverLicenseNumber;
    
    @Column(name = "license_expiry_date")
    private LocalDate licenseExpiryDate;
    
    // Pricing
    @Column(name = "base_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;
    
    @Column(name = "driver_fee", precision = 10, scale = 2)
    private BigDecimal driverFee = BigDecimal.ZERO;
    
    @Column(name = "total_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost;
    
    @Column(name = "number_of_days", nullable = false)
    private Integer numberOfDays;
    
    // Payment Information
    @Column(name = "payment_intent_id")
    private String paymentIntentId;
    
    @Column(name = "payment_status", nullable = false)
    private String paymentStatus = "PENDING"; // PENDING, PAID, FAILED, REFUNDED
    
    @Column(name = "payment_method")
    private String paymentMethod;
    
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;
    
    // Booking Status
    @Column(name = "booking_status", nullable = false)
    private String bookingStatus = "PENDING"; // PENDING, CONFIRMED, CANCELLED, COMPLETED
    
    // Contact Information
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "phone", nullable = false)
    private String phone;
    
    // Additional Information
    @Column(name = "special_requests", columnDefinition = "TEXT")
    private String specialRequests;
    
    // Metadata
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;
    
    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    private String cancellationReason;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
