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
            if (request.getBookingId() != null) {
                HotelBooking booking = hotelBookingService.getBookingById(request.getBookingId());
                if (booking == null) {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "Booking not found");
                    return ResponseEntity.badRequest().body(error);
                }
            }
            
            PaymentIntentResponse response = stripePaymentService.createPaymentIntent(request);
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Payment processing error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * Confirm payment for a booking
     */
    @PostMapping("/confirm-payment/{paymentIntentId}")
    public ResponseEntity<?> confirmPayment(@PathVariable String paymentIntentId) {
        try {
            HotelBooking booking = hotelBookingService.confirmPayment(paymentIntentId);
            HotelBookingResponse response = hotelBookingService.toResponse(booking);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
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
