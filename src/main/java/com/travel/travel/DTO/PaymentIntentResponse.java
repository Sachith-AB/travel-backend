package com.travel.travel.DTO;

import lombok.Data;

@Data
public class PaymentIntentResponse {
    private String clientSecret;
    private String paymentIntentId;
    private String status;
}
