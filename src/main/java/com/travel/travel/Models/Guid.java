package com.travel.travel.Models;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "guides")
public class Guid {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "bio", length = 2000)
    private String bio;

    @ElementCollection
    @CollectionTable(name = "guid_languages", joinColumns = @JoinColumn(name = "guid_id"))
    @Column(name = "language")
    private List<String> languagesSpoken;

    @ElementCollection
    @CollectionTable(name = "guid_specializations", joinColumns = @JoinColumn(name = "guid_id"))
    @Column(name = "specialization")
    private List<String> specialization;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "hours_rate")
    private Double hoursRate;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "slta_license_id")
    private String sltaLicenseId;

    @ElementCollection
    @CollectionTable(name = "guid_slta_license_photos", joinColumns = @JoinColumn(name = "guid_id"))
    @Column(name = "slta_license_photo")
    private List<String> sltaLicensePhoto;

    @Column(name = "slta_license_expiry")
    private LocalDateTime sltaLicenseExpiry;

    @Column(name = "nic_number")
    private String nicNumber;

    @ElementCollection
    @CollectionTable(name = "guid_nic_photos", joinColumns = @JoinColumn(name = "guid_id"))
    @Column(name = "nic_photo_front")
    private List<String> nicPhotoFront;

    @ElementCollection
    @CollectionTable(name = "guid_nic_photos_back", joinColumns = @JoinColumn(name = "guid_id"))
    @Column(name = "nic_photo_back")
    private List<String> nicPhotoBack;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
