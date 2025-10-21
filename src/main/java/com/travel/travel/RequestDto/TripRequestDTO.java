package com.travel.travel.RequestDto;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.travel.travel.Models.Enum.TripStatus;

import lombok.Data;

@Data
public class TripRequestDTO {
    private String tripCode;
    private Long userId;  
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