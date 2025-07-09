package com.travel.travel.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;   //fk relationship to User entity

    @Column(name = "age")
    private int age;

    @Column(name = "experience")
    private int experience;

    @Column(name = "driver_license_number")
    private String driverLicenseNumber;

    @Column(name = "driver_license_photo")
    private String driverLicensePhoto;

    @Column(name = "license_expiry_date")
    private LocalDate licenseExpiryDate;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private VehicleAgency agency;  //fk to vehicle agency

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
