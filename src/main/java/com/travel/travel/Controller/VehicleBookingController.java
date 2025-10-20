package com.travel.travel.Controller;

import com.stripe.exception.StripeException;
import com.travel.travel.DTO.PaymentIntentRequest;
import com.travel.travel.DTO.PaymentIntentResponse;
import com.travel.travel.DTO.VehicleBookingRequest;
import com.travel.travel.Models.VehicleBooking;
import com.travel.travel.Service.VehicleBookingService;
import com.travel.travel.Service.StripePaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings/vehicles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VehicleBookingController {
    
    private final VehicleBookingService vehicleBookingService;
    private final StripePaymentService stripePaymentService;
    
    /**
     * Create a new vehicle booking
     */
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody VehicleBookingRequest request) {
        try {
            System.out.println("=== Creating Vehicle Booking ===");
            System.out.println("Request: " + request);
            
            VehicleBooking booking = vehicleBookingService.createBooking(request);
            
            System.out.println("✅ Vehicle booking created successfully with ID: " + booking.getId());
            System.out.println("Saved to vehicle_bookings table");
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", booking.getId());
            response.put("bookingId", booking.getId());
            response.put("vehicleId", booking.getVehicle().getId());
            response.put("userId", booking.getUser().getId());
            response.put("pickupDate", booking.getPickupDate());
            response.put("returnDate", booking.getReturnDate());
            response.put("pickupLocation", booking.getPickupLocation());
            response.put("dropoffLocation", booking.getDropoffLocation());
            response.put("totalCost", booking.getTotalCost());
            response.put("numberOfDays", booking.getNumberOfDays());
            response.put("bookingStatus", booking.getBookingStatus());
            response.put("paymentStatus", booking.getPaymentStatus());
            response.put("firstName", booking.getFirstName());
            response.put("lastName", booking.getLastName());
            response.put("email", booking.getEmail());
            response.put("phone", booking.getPhone());
            response.put("createdAt", booking.getCreatedAt());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("❌ Error creating vehicle booking: " + e.getMessage());
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
    public ResponseEntity<?> getBookingById(@PathVariable Long id) {
        try {
            VehicleBooking booking = vehicleBookingService.getBookingById(id);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    /**
     * Get bookings by user ID
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBookingsByUserId(@PathVariable Long userId) {
        try {
            List<VehicleBooking> bookings = vehicleBookingService.getBookingsByUserId(userId);
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    /**
     * Get bookings by vehicle ID
     */
    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<?> getBookingsByVehicleId(@PathVariable Long vehicleId) {
        try {
            List<VehicleBooking> bookings = vehicleBookingService.getBookingsByVehicleId(vehicleId);
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    /**
     * Cancel booking
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(
        @PathVariable Long id,
        @RequestBody(required = false) Map<String, String> request
    ) {
        try {
            String reason = request != null ? request.get("reason") : null;
            vehicleBookingService.cancelBooking(id, reason);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Booking cancelled successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    /**
     * Create payment intent for vehicle booking
     */
    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentIntentRequest request) {
        try {
            System.out.println("=== Creating Payment Intent for Vehicle Booking ===");
            System.out.println("Amount: " + request.getAmount());
            System.out.println("Currency: " + request.getCurrency());
            
            PaymentIntentResponse response = stripePaymentService.createPaymentIntent(request);
            System.out.println("✅ Payment intent created: " + response.getPaymentIntentId());
            
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            System.err.println("❌ Stripe error: " + e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", "Payment processing error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            System.err.println("❌ Error creating payment intent: " + e.getMessage());
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
