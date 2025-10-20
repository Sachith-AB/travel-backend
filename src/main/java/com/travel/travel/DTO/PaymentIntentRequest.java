package com.travel.travel.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PaymentIntentRequest {
    private BigDecimal amount;
    private String currency;
    private String description;
    private Long bookingId;
    private String bookingType; // HOTEL, VEHICLE, GUIDE, TRIP
}
