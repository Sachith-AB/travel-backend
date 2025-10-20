package com.travel.travel.Controller;

import com.stripe.exception.StripeException;
import com.travel.travel.DTO.HotelBookingRequest;
import com.travel.travel.DTO.HotelBookingResponse;
import com.travel.travel.DTO.PaymentIntentRequest;
import com.travel.travel.DTO.PaymentIntentResponse;
import com.travel.travel.Models.HotelBooking;
import com.travel.travel.Service.HotelBookingService;
import com.travel.travel.Service.StripePaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hotel-bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HotelBookingController {
    
    private final HotelBookingService hotelBookingService;
    private final StripePaymentService stripePaymentService;
    
    /**
     * Create a new hotel booking
     */
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody HotelBookingRequest request) {
        try {
            HotelBooking booking = hotelBookingService.createBooking(request);
            HotelBookingResponse response = hotelBookingService.toResponse(booking);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    /**
     * Create payment intent for hotel booking
     */
    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentIntentRequest request) {
        try {
            // Validate the booking exists if bookingId is provided
            HotelBooking booking = null;
            if (request.getBookingId() != null) {
                booking = hotelBookingService.getBookingById(request.getBookingId());
                if (booking == null) {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "Booking not found with ID: " + request.getBookingId());
                    return ResponseEntity.badRequest().body(error);
                }
                
                System.out.println("Found booking: " + booking.getId() + ", Reference: " + booking.getBookingReference());
            }
            
            PaymentIntentResponse response = stripePaymentService.createPaymentIntent(request);
            System.out.println("Payment intent created: " + response.getPaymentIntentId());
            
            // Update booking with payment intent ID
            if (booking != null && response.getPaymentIntentId() != null) {
                booking.setStripePaymentIntentId(response.getPaymentIntentId());
                HotelBooking updatedBooking = hotelBookingService.updateBooking(booking);
                System.out.println("Booking updated with payment intent ID: " + updatedBooking.getStripePaymentIntentId());
            }
            
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            System.err.println("Stripe error: " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Payment processing error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            System.err.println("Error creating payment intent: " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * Confirm payment for a booking
     */
    @PostMapping("/confirm-payment/{paymentIntentId}")
    public ResponseEntity<?> confirmPayment(@PathVariable String paymentIntentId) {
        try {
            System.out.println("Confirming payment for payment intent: " + paymentIntentId);
            
            // First, verify the payment intent status with Stripe
            PaymentIntentResponse paymentIntent = stripePaymentService.retrievePaymentIntent(paymentIntentId);
            System.out.println("Payment intent status from Stripe: " + paymentIntent.getStatus());
            
            // Only confirm booking if payment succeeded
            if (!"succeeded".equals(paymentIntent.getStatus())) {
                System.err.println("Payment not successful. Status: " + paymentIntent.getStatus());
                Map<String, String> error = new HashMap<>();
                error.put("error", "Payment not successful. Status: " + paymentIntent.getStatus());
                return ResponseEntity.badRequest().body(error);
            }
            
            // Update booking status
            HotelBooking booking = hotelBookingService.confirmPayment(paymentIntentId);
            System.out.println("Booking confirmed: " + booking.getBookingReference());
            
            HotelBookingResponse response = hotelBookingService.toResponse(booking);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error confirming payment: " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    /**
     * Create a new hotel booking WITH payment (after payment succeeds)
     * This endpoint should be called AFTER payment is successful
     */
    @PostMapping("/create-with-payment")
    public ResponseEntity<?> createBookingWithPayment(@RequestBody HotelBookingRequest request) {
        try {
            System.out.println("Creating booking with payment intent: " + request.getStripePaymentIntentId());
            
            // Verify the payment intent status with Stripe first
            if (request.getStripePaymentIntentId() != null) {
                try {
                    PaymentIntentResponse paymentIntent = stripePaymentService.retrievePaymentIntent(
                        request.getStripePaymentIntentId()
                    );
                    
                    System.out.println("Payment intent status: " + paymentIntent.getStatus());
                    
                    if (!"succeeded".equals(paymentIntent.getStatus())) {
                        Map<String, String> error = new HashMap<>();
                        error.put("error", "Payment not successful. Status: " + paymentIntent.getStatus());
                        return ResponseEntity.badRequest().body(error);
                    }
                } catch (Exception e) {
                    System.err.println("Error verifying payment intent: " + e.getMessage());
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "Failed to verify payment: " + e.getMessage());
                    return ResponseEntity.badRequest().body(error);
                }
            }
            
            // Create the booking
            HotelBooking booking = hotelBookingService.createBooking(request);
            
            // Set payment status to completed since payment is already done
            booking.setPaymentStatus("COMPLETED");
            booking.setBookingStatus("CONFIRMED");
            booking.setPaymentDate(LocalDateTime.now());
            booking.setPaymentMethod("STRIPE");
            
            // Save updated booking
            booking = hotelBookingService.updateBooking(booking);
            
            System.out.println("Booking created and confirmed: " + booking.getBookingReference());
            
            HotelBookingResponse response = hotelBookingService.toResponse(booking);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error creating booking with payment: " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    /**
     * Get booking by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getBooking(@PathVariable Long id) {
        HotelBooking booking = hotelBookingService.getBookingById(id);
        if (booking == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Booking not found");
            return ResponseEntity.notFound().build();
        }
        
        HotelBookingResponse response = hotelBookingService.toResponse(booking);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get booking by reference
     */
    @GetMapping("/reference/{reference}")
    public ResponseEntity<?> getBookingByReference(@PathVariable String reference) {
        HotelBooking booking = hotelBookingService.getBookingByReference(reference);
        if (booking == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Booking not found");
            return ResponseEntity.notFound().build();
        }
        
        HotelBookingResponse response = hotelBookingService.toResponse(booking);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get all bookings for a user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserBookings(@PathVariable Long userId) {
        List<HotelBooking> bookings = hotelBookingService.getBookingsByUserId(userId);
        List<HotelBookingResponse> responses = bookings.stream()
            .map(hotelBookingService::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Get all bookings for a hotel
     */
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<?> getHotelBookings(@PathVariable Long hotelId) {
        List<HotelBooking> bookings = hotelBookingService.getBookingsByHotelId(hotelId);
        List<HotelBookingResponse> responses = bookings.stream()
            .map(hotelBookingService::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Cancel a booking
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(
        @PathVariable Long id,
        @RequestBody Map<String, String> request
    ) {
        try {
            String reason = request.getOrDefault("reason", "Customer requested cancellation");
            HotelBooking booking = hotelBookingService.cancelBooking(id, reason);
            HotelBookingResponse response = hotelBookingService.toResponse(booking);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
