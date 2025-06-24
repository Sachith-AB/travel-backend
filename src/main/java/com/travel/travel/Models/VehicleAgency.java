package com.travel.travel.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "vehicle_agencies")
public class VehicleAgency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "agency_name")
    private String agencyName;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "province")
    private String province;

    @Column(name = "registration_no")
    private String registrationNo;

    @ElementCollection
    @CollectionTable(name = "agency_license_photos", joinColumns = @JoinColumn(name = "agency_id"))
    @Column(name = "license_photo_url")
    private List<String> licensePhoto;

    @Column(name = "description", length = 2000)
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
