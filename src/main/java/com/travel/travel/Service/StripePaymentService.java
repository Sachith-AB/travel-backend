package com.travel.travel.Service;

import com.stripe.exception.StripeException;
import com.travel.travel.DTO.PaymentIntentRequest;
import com.travel.travel.DTO.PaymentIntentResponse;

public interface StripePaymentService {
    
    /**
     * Create a payment intent for Stripe
     * @param request Payment intent request details
     * @return Payment intent response with client secret
     * @throws StripeException if there's an error with Stripe API
     */
    PaymentIntentResponse createPaymentIntent(PaymentIntentRequest request) throws StripeException;
    
    /**
     * Confirm a payment intent
     * @param paymentIntentId The payment intent ID
     * @return Payment intent response
     * @throws StripeException if there's an error with Stripe API
     */
    PaymentIntentResponse confirmPaymentIntent(String paymentIntentId) throws StripeException;
    
    /**
     * Cancel a payment intent
     * @param paymentIntentId The payment intent ID
     * @return Payment intent response
     * @throws StripeException if there's an error with Stripe API
     */
    PaymentIntentResponse cancelPaymentIntent(String paymentIntentId) throws StripeException;
    
    /**
     * Retrieve a payment intent
     * @param paymentIntentId The payment intent ID
     * @return Payment intent response
     * @throws StripeException if there's an error with Stripe API
     */
    PaymentIntentResponse retrievePaymentIntent(String paymentIntentId) throws StripeException;
    
    /**
     * Create a refund for a payment
     * @param chargeId The charge ID to refund
     * @param amount The amount to refund (null for full refund)
     * @return Refund ID
     * @throws StripeException if there's an error with Stripe API
     */
    String createRefund(String chargeId, Long amount) throws StripeException;
}
