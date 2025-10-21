package com.travel.travel.Controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.travel.travel.DTO.PaymentIntentRequest;
import com.travel.travel.DTO.PaymentIntentResponse;
import com.travel.travel.Models.Trip;
import com.travel.travel.Service.StripePaymentService;
import com.travel.travel.Service.TripService;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    TripService tripService;
    
    @Autowired
    StripePaymentService stripePaymentService;

    @PostMapping
    public ResponseEntity<?> createTrip(@RequestBody Trip trip) {
        try {
            System.out.println("=== RECEIVED TRIP REQUEST ===");
            System.out.println("Trip Code: " + trip.getTripCode());
            System.out.println("User: " + (trip.getUser() != null ? trip.getUser().getId() : "NULL"));
            System.out.println("Trip Status: " + trip.getTripStatus());
            System.out.println("Pickup Location: " + trip.getPickupLocation());
            System.out.println("Start Date: " + trip.getTripStartDate());
            System.out.println("Start Time: " + trip.getStartTime());
            System.out.println("Number of Adults: " + trip.getNumberOfAdults());
            System.out.println("Number of Kids: " + trip.getNumberOfKids());
            System.out.println("Selected Vehicle: " + (trip.getSelectedVehicle() != null ? trip.getSelectedVehicle().getId() : "NULL"));
            System.out.println("Selected Vehicle Agency: " + (trip.getSelectedVehicleAgency() != null ? trip.getSelectedVehicleAgency().getId() : "NULL"));
            System.out.println("Selected Guide: " + (trip.getSelectedGuide() != null ? trip.getSelectedGuide().getId() : "NULL"));
            System.out.println("Selected Hotels Count: " + (trip.getSelectedHotels() != null ? trip.getSelectedHotels().size() : "0"));
            System.out.println("Selected Rooms Count: " + (trip.getSelectedRooms() != null ? trip.getSelectedRooms().size() : "0"));
            System.out.println("Base Price: " + trip.getBasePrice());
            System.out.println("Total Fare: " + trip.getTotalFare());
            System.out.println("Full Name: " + trip.getFullName());
            System.out.println("Email: " + trip.getEmail());
            System.out.println("Phone: " + trip.getPhone());
            System.out.println("============================");
            
            Trip savedTrip = tripService.createTrip(trip);
            
            System.out.println("✅ Trip saved successfully with ID: " + savedTrip.getId());
            return ResponseEntity.ok(savedTrip);
        } catch (Exception e) {
            System.err.println("❌ ERROR saving trip: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error creating trip: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable Long id) throws Exception {
        System.out.println("Requested Trip ID: " + id);
        try{
            Optional<Trip> tripOptional = tripService.tripGetById(id);
            if (tripOptional.isPresent()) {
                Trip trip = tripOptional.get();
                System.out.println(" Found trip: " + trip.getTripCode());
                return ResponseEntity.ok(trip);
            } else {
                System.out.println("Trip not found with ID: " + id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            System.err.println(" Error fetching trip: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTripsByUserId(@PathVariable Long userId) {
        try {
            List<Trip> trips = tripService.getTripsByUserId(userId);
            return ResponseEntity.ok(trips);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateTripStatus(@PathVariable Long id, @RequestBody Map<String, String> statusUpdate) {
        try {
            String newStatus = statusUpdate.get("status");
            if (newStatus == null || newStatus.isEmpty()) {
                return ResponseEntity.badRequest().body("Status is required");
            }
            
            Trip updatedTrip = tripService.updateTripStatus(id, newStatus);
            return ResponseEntity.ok(updatedTrip);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrip(@PathVariable Long id, @RequestBody Trip trip) {
        try {
            Trip updatedTrip = tripService.updateTrip(id, trip);
            return ResponseEntity.ok(updatedTrip);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    
    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentIntentRequest request) {
        try {
            
            Trip trip = null;
            if (request.getBookingId() != null) {
                Optional<Trip> tripOptional = tripService.tripGetById(request.getBookingId());
                if (!tripOptional.isPresent()) {
                    Map<String, String> error = new HashMap<>();
                    error.put("error", "Trip not found with ID: " + request.getBookingId());
                    return ResponseEntity.badRequest().body(error);
                }
                trip = tripOptional.get();
                System.out.println("Found trip: " + trip.getTripCode());
            }
            
            PaymentIntentResponse response = stripePaymentService.createPaymentIntent(request);
            System.out.println("Payment intent created: " + response.getPaymentIntentId());
            
            
            if (trip != null && response.getPaymentIntentId() != null) {
                trip.setStripePaymentIntentId(response.getPaymentIntentId());
                Trip updatedTrip = tripService.updateTrip(trip.getId(), trip);
                System.out.println("Trip updated with payment intent ID: " + updatedTrip.getStripePaymentIntentId());
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
            error.put("error", "Failed to create payment intent: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
