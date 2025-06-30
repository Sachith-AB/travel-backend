package com.travel.travel.Dtos;

import com.travel.travel.Models.Enum.TripStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Data
public class TripRequestDTO {
    private String tripCode;
    private Long userId;  // Just the ID instead of full User object
    private String pickupLocation;
    private Date tripStartDate;
    private String tripEndDate;
    private Time startTime;
    private List<Long> placesToBeVisit;
    private Integer numberOfAdults;
    private Integer numberOfKids;
    private String estimateDuration;
    private Integer distanceKm;
    private TripStatus tripStatus;
    private Long selectedVehicleAgencyId;
    private Long selectedVehicleId;
    private Long selectedHotelId;
    private BigDecimal basePrice;
    private BigDecimal totalFare;
}