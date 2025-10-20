package com.travel.travel.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HotelBookingResponse {
    private Long id;
    private String bookingReference;
    
    private String userEmail;
    private String userName;
    private String userPhone;
    
    private Long hotelId;
    private String hotelName;
    private Long roomId;
    private String roomType;
    
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfNights;
    private Integer numberOfGuests;
    
    private BigDecimal pricePerNight;
    private BigDecimal totalAmount;
    private String currency;
    
    private String paymentStatus;
    private String bookingStatus;
    private String specialRequests;
    
    private LocalDateTime createdAt;
}
