package com.travel.travel.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class HotelBookingRequest {
    private Long userId;
    private String userEmail;
    private String userName;
    private String userPhone;
    
    private Long hotelId;
    private Long roomId;
    
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfGuests;
    
    private String specialRequests;
    
    // This will be populated from Stripe
    private String stripePaymentIntentId;
}
