// src/main/java/com/travel/travel/Models/Hotel.java
package com.travel.travel.Models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "hotel_name")
    private String hotelName;

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
}