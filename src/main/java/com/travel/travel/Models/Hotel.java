// src/main/java/com/travel/travel/Models/Hotel.java
package com.travel.travel.Models;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "hotels")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "hotel_name")
    private String hotelName;

    @Column(name = "street")
    private String street;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    

    @Column(name = "province")
    private String province;

    @Column(name = "registration_no")
    private String registrationNo;

    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @ElementCollection
    @CollectionTable(name = "hotel_license_photos", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "license_photo_url")
    private List<String> licensePhoto;

    @ElementCollection
    @CollectionTable(name = "hotel_images", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "image_url")
    private List<String> images;

    @Column(name = "type")
    private String type;

    @Column(name = "description", length = 2000)
    private String description;

    @ElementCollection
    @CollectionTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "amenity")
    private List<String> amenities;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"hotel", "hibernateLazyInitializer", "handler"})
    private List<Room> rooms;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;
}