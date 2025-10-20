package com.travel.travel.Service.ServiceImpl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;
import com.travel.travel.DTO.PaymentIntentRequest;
import com.travel.travel.DTO.PaymentIntentResponse;
import com.travel.travel.Service.StripePaymentService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripePaymentServiceImpl implements StripePaymentService {
    
    @Value("${stripe.api.key:}")
    private String stripeApiKey;
    
    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }
    
    @Override
    public PaymentIntentResponse createPaymentIntent(PaymentIntentRequest request) throws StripeException {
        // Convert amount to cents (Stripe uses smallest currency unit)
        long amountInCents = request.getAmount().multiply(BigDecimal.valueOf(100)).longValue();
        
        // Create metadata
        Map<String, String> metadata = new HashMap<>();
        if (request.getBookingId() != null) {
            metadata.put("bookingId", String.valueOf(request.getBookingId()));
        }
        if (request.getBookingType() != null) {
            metadata.put("bookingType", request.getBookingType());
        }
        
        // Use USD as default currency for better Stripe compatibility
        String currency = request.getCurrency() != null ? request.getCurrency().toLowerCase() : "usd";
        
        // Build payment intent parameters
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
            .setAmount(amountInCents)
            .setCurrency(currency)
            .setDescription(request.getDescription())
            .putAllMetadata(metadata)
            .setAutomaticPaymentMethods(
                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                    .setEnabled(true)
                    .build()
            )
            .build();
        
        PaymentIntent paymentIntent = PaymentIntent.create(params);
        
        return mapToResponse(paymentIntent);
    }
    
    @Override
    public PaymentIntentResponse confirmPaymentIntent(String paymentIntentId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        PaymentIntent confirmedIntent = paymentIntent.confirm();
        
        return mapToResponse(confirmedIntent);
    }
    
    @Override
    public PaymentIntentResponse cancelPaymentIntent(String paymentIntentId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        PaymentIntent cancelledIntent = paymentIntent.cancel();
        
        return mapToResponse(cancelledIntent);
    }
    
    @Override
    public PaymentIntentResponse retrievePaymentIntent(String paymentIntentId) throws StripeException {
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        
        return mapToResponse(paymentIntent);
    }
    
    @Override
    public String createRefund(String chargeId, Long amount) throws StripeException {
        RefundCreateParams.Builder paramsBuilder = RefundCreateParams.builder()
            .setCharge(chargeId);
        
        if (amount != null) {
            paramsBuilder.setAmount(amount);
        }
        
        Refund refund = Refund.create(paramsBuilder.build());
        
        return refund.getId();
    }
    
    private PaymentIntentResponse mapToResponse(PaymentIntent paymentIntent) {
        PaymentIntentResponse response = new PaymentIntentResponse();
        response.setPaymentIntentId(paymentIntent.getId());
        response.setClientSecret(paymentIntent.getClientSecret());
        response.setStatus(paymentIntent.getStatus());
        
        return response;
    }
}
