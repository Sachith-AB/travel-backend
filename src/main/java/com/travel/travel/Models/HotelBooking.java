package com.travel.travel.Models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "hotel_bookings")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HotelBooking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "booking_reference", unique = true, nullable = false)
    private String bookingReference;
    
    // User Information
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;
    
    @Column(name = "user_email", nullable = false)
    private String userEmail;
    
    @Column(name = "user_name", nullable = false)
    private String userName;
    
    @Column(name = "user_phone")
    private String userPhone;
    
    // Hotel & Room Information
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "rooms"})
    private Hotel hotel;
    
    @Column(name = "hotel_name", nullable = false)
    private String hotelName;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "hotel"})
    private Room room;
    
    @Column(name = "room_type", nullable = false)
    private String roomType;
    
    // Booking Details
    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;
    
    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;
    
    @Column(name = "number_of_nights", nullable = false)
    private Integer numberOfNights;
    
    @Column(name = "number_of_guests", nullable = false)
    private Integer numberOfGuests;
    
    // Pricing Information
    @Column(name = "price_per_night", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerNight;
    
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;
    
    @Column(name = "currency")
    private String currency = "LKR";
    
    // Payment Information
    @Column(name = "payment_status", nullable = false)
    private String paymentStatus = "PENDING"; // PENDING, COMPLETED, FAILED, REFUNDED
    
    @Column(name = "payment_method")
    private String paymentMethod;
    
    @Column(name = "stripe_payment_intent_id")
    private String stripePaymentIntentId;
    
    @Column(name = "stripe_charge_id")
    private String stripeChargeId;
    
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;
    
    // Booking Status
    @Column(name = "booking_status", nullable = false)
    private String bookingStatus = "PENDING"; // PENDING, CONFIRMED, CANCELLED, COMPLETED
    
    // Special Requests
    @Column(name = "special_requests", columnDefinition = "TEXT")
    private String specialRequests;
    
    // Timestamps
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    // Cancellation Details
    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;
    
    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    private String cancellationReason;
    
    @Column(name = "refund_amount", precision = 10, scale = 2)
    private BigDecimal refundAmount;
    
    @Column(name = "refund_status")
    private String refundStatus;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (bookingReference == null) {
            bookingReference = generateBookingReference();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    private String generateBookingReference() {
        return "HB-" + System.currentTimeMillis();
    }
}
