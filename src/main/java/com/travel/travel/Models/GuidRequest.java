package com.travel.travel.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

@Entity
@Data
@Table(name = "guid_requests")
public class GuidRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trip_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Trip trip;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guid_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Guid guid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guide_booking_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private GuideBooking guideBooking;

    @Column(name = "booking_type")
    private String bookingType; // TOUR or INDIVIDUAL

    @Column(name = "status")
    private String status; // PENDING, APPROVED, REJECTED, CANCELLED
    
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @Column(name = "number_of_days")
    private Integer numberOfDays;
    
    @Column(name = "number_of_people")
    private Integer numberOfPeople;
    
    @Column(name = "locations", columnDefinition = "TEXT")
    private String locations;
    
    @Column(name = "total_price")
    private Double totalPrice;
    
    @Column(name = "traveler_name")
    private String travelerName;
    
    @Column(name = "traveler_email")
    private String travelerEmail;
    
    @Column(name = "request_date")
    private LocalDateTime requestDate;
    
    @Column(name = "multi_request_id")
    private String multiRequestId;
    
    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "payment_status")
    private String paymentStatus;
}

