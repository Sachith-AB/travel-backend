package com.travel.travel.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity representing a standalone guide booking (not part of a tour)
 * Used when users book guides independently
 */
@Entity
@Data
@Table(name = "guide_bookings")
public class GuideBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password"})
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guide_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Guid guide;

    @Column(name = "locations", columnDefinition = "TEXT")
    private String locations;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "number_of_days")
    private Integer numberOfDays;

    @Column(name = "number_of_people")
    private Integer numberOfPeople;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "special_requests", columnDefinition = "TEXT")
    private String specialRequests;

    @Column(name = "preferred_language")
    private String preferredLanguage;

    @Column(name = "accommodation_needed")
    private Boolean accommodationNeeded = false;

    @Column(name = "transportation_needed")
    private Boolean transportationNeeded = false;

    @Column(name = "meal_preferences")
    private String mealPreferences;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "status")
    private String status; // PENDING, APPROVED, REJECTED, PAID, COMPLETED, CANCELLED

    @Column(name = "payment_status")
    private String paymentStatus; // PENDING, PAID, REFUNDED

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    private String cancellationReason;

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;

    @Column(name = "multi_request_id")
    private String multiRequestId; // Groups bookings from same multi-guide request

    @Column(name = "selected_guide_ids", columnDefinition = "TEXT")
    private String selectedGuideIds; // Comma-separated list of all guide IDs requested

    @Column(name = "approved_guide_id")
    private Long approvedGuideId; // The guide who actually approved and will fulfill the booking

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
