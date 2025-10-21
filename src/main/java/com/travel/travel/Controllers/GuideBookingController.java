package com.travel.travel.Controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travel.travel.DTOs.GroupedGuideBookingDTO;
import com.travel.travel.DTOs.GuideBookingRequest;
import com.travel.travel.Models.GuideBooking;
import com.travel.travel.Services.GuideBookingService;

@RestController
@RequestMapping("/api/guide-bookings")
public class GuideBookingController {

    @Autowired
    private GuideBookingService guideBookingService;

    /**
     * Create a new guide booking
     * POST /api/guide-bookings
     */
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody GuideBookingRequest request) {
        try {
            GuideBooking createdBooking = guideBookingService.createBooking(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    
    @GetMapping("/my-bookings")
    public ResponseEntity<?> getMyBookings(@RequestParam Long userId) {
        try {
            List<GuideBooking> bookings = guideBookingService.getMyBookings(userId);
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    
    @GetMapping("/my-bookings/grouped")
    public ResponseEntity<?> getMyBookingsGrouped(@RequestParam Long userId) {
        try {
            List<GroupedGuideBookingDTO> groupedBookings = guideBookingService.getMyBookingsGrouped(userId);
            return ResponseEntity.ok(groupedBookings);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

   
    @GetMapping("/requests")
    public ResponseEntity<?> getGuideRequests(@RequestParam Long guideId) {
        try {
            List<GuideBooking> bookings = guideBookingService.getGuideRequests(guideId);
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Long id) {
        try {
            return guideBookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    
    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveBooking(@PathVariable Long id, @RequestParam Long guideId) {
        try {
            GuideBooking booking = guideBookingService.approveBooking(id, guideId);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectBooking(
        @PathVariable Long id, 
        @RequestParam Long guideId,
        @RequestBody Map<String, String> payload
    ) {
        try {
            String reason = payload.get("reason");
            GuideBooking booking = guideBookingService.rejectBooking(id, guideId, reason);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * User pays for an approved booking
     * POST /api/guide-bookings/{id}/pay
     */
    @PostMapping("/{id}/pay")
    public ResponseEntity<?> payBooking(@PathVariable Long id, @RequestParam Long userId) {
        try {
            GuideBooking booking = guideBookingService.payBooking(id, userId);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Cancel a booking
     * PUT /api/guide-bookings/{id}/cancel
     */
    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(
        @PathVariable Long id, 
        @RequestParam Long userId,
        @RequestBody Map<String, String> payload
    ) {
        try {
            String reason = payload.get("reason");
            GuideBooking booking = guideBookingService.cancelBooking(id, userId, reason);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Complete a booking
     * PUT /api/guide-bookings/{id}/complete
     */
    @PutMapping("/{id}/complete")
    public ResponseEntity<?> completeBooking(@PathVariable Long id, @RequestParam Long guideId) {
        try {
            GuideBooking booking = guideBookingService.completeBooking(id, guideId);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Check guide availability
     * GET /api/guide-bookings/check-availability
     */
    @GetMapping("/check-availability")
    public ResponseEntity<?> checkAvailability(
        @RequestParam Long guideId,
        @RequestParam String startDate,
        @RequestParam String endDate
    ) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            
            boolean available = guideBookingService.checkAvailability(guideId, start, end);
            
            Map<String, Object> response = new HashMap<>();
            response.put("available", available);
            response.put("guideId", guideId);
            response.put("startDate", startDate);
            response.put("endDate", endDate);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
