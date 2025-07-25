package com.travel.travel.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "vehicle_no")
    private String vehicleNo;

    @Column(name = "registration_no")
    private String registrationNo;

    @Column(name = "price_per_kilometer")
    private String pricePerKilometer;

    @Column(name = "base_price")
    private BigDecimal basePrice;

    @Column(name = "insurance_number")
    private String insuranceNumber;

    @Column(name = "insurance_expiry_date")
    private LocalDate insuranceExpiryDate;

    @Column(name = "vehicle_model")
    private String vehicleModel;

    @Column(name = "is_verified")
    private boolean isVerified;

    @ElementCollection
    @CollectionTable(name = "vehicle_images", joinColumns = @JoinColumn(name = "vehicle_id"))
    @Column(name = "image_url")
    private List<String> images;

    @ElementCollection
    @CollectionTable(name = "vehicle_amenities", joinColumns = @JoinColumn(name = "vehicle_id"))
    @Column(name = "amenity")
    private List<String> amenities;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "availability")
    private boolean availability;

    @Column(name = "capacity")
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "agency_id") // This will create the foreign key
    private VehicleAgency agency;
}
