package com.travel.travel.Models;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.travel.travel.Models.Enum.TripStatus;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "trips")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "trip_code")
    private String tripCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "trip"})
    private User user;

    @Column(name = "pickup_location")
    private String pickupLocation;

    @Column(name = "trip_start_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date tripStartDate;

    @Column(name = "trip_end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date tripEndDate;

    @Column(name = "start_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private Time startTime;

    @ElementCollection
    @CollectionTable(name = "trip_places", joinColumns = @JoinColumn(name = "trip_id"))
    @Column(name = "place_id")
    private List<Long> placesToBeVisit;

    @Column(name = "number_of_adults")
    private Integer numberOfAdults;

    @Column(name = "number_of_kids")
    private Integer numberOfKids;

    @Column(name = "estimate_duration")
    private String estimateDuration; 

    @Column(name = "distance_km")
    private Integer distanceKm;  

    @Column(name = "trip_status")
    @Enumerated(EnumType.STRING)
    private TripStatus tripStatus;

    public Trip() {
        this.tripStatus = TripStatus.pending; 
    }



    //@ManyToMany
//    @JoinTable(
//            name = "trip_requested_guides",
//            joinColumns = @JoinColumn(name = "trip_id"),
//            inverseJoinColumns = @JoinColumn(name = "guide_id")
//    )
    //private List<Guide> requestedGuideList;

    //@ManyToOne
    //@JoinColumn(name = "accepted_guide", referencedColumnName = "id")
    //private Guide acceptedGuide;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "selected_vehicle_agency", referencedColumnName = "id")
    private VehicleAgency selectedVehicleAgency;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "selected_vehicle", referencedColumnName = "id")
    private Vehicle selectedVehicle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "selected_hotel", referencedColumnName = "id")
    private Hotel selectedHotel;

    @ManyToMany
    @JoinTable(
            name = "trip_selected_rooms",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private List<Room> selectedRooms;

    @Column(name = "base_price", precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "total_fare", precision = 10, scale = 2)
    private BigDecimal totalFare;

    // Additional fields for user details
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "country")
    private String country;

    @Column(name = "nic_number")
    private String nicNumber;

    @Column(name = "optional_contact")
    private String optionalContact;

    @Column(name = "special_requests")
    private String specialRequests;

    @Column(name = "age_group")
    private String ageGroup;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "travel_experience")
    private String travelExperience;

    @Column(name = "referral_source")
    private String referralSource;

    @Column(name = "destination")
    private String destination;

    @Column(name = "duration")
    private String duration;

    @Column(name = "travel_style")
    private String travelStyle;

    @Column(name = "group_type")
    private String groupType;

    @ElementCollection
    @CollectionTable(name = "trip_interests", joinColumns = @JoinColumn(name = "trip_id"))
    @Column(name = "interest")
    private List<String> interests;

    @Column(name = "accommodation_preference")
    private String accommodationPreference;

    @Column(name = "budget_range")
    private String budgetRange;

    @Column(name = "activity_level")
    private String activityLevel;

    @Column(name = "dining_preference")
    private String diningPreference;

    @Column(name = "itinerary", columnDefinition = "TEXT")
    private String itineraryJson; 
}

