package com.travel.travel.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.travel.DTOs.GroupedGuideBookingDTO;
import com.travel.travel.DTOs.GuideBookingRequest;
import com.travel.travel.Models.Guid;
import com.travel.travel.Models.GuidRequest;
import com.travel.travel.Models.GuideBooking;
import com.travel.travel.Models.User;
import com.travel.travel.Repository.GuidRepository;
import com.travel.travel.Repository.GuidRequestRepository;
import com.travel.travel.Repository.GuideBookingRepository;
import com.travel.travel.Repository.UserRepository;

@Service
public class GuideBookingService {

    @Autowired
    private GuideBookingRepository guideBookingRepository;

    @Autowired
    private GuidRepository guidRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GuidRequestRepository guidRequestRepository;

    /**
     * Create a new guide booking from DTO
     * Supports both single and multi-guide bookings
     * Creates corresponding guid_requests entries for tracking
     */
    @Transactional
    public GuideBooking createBooking(GuideBookingRequest request) {
        // Validate user exists
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getUserId()));
        
        // Determine if this is a multi-guide booking
        List<Long> guideIds = new ArrayList<>();
        if (request.getGuideIds() != null && !request.getGuideIds().isEmpty()) {
            guideIds = request.getGuideIds();
        } else if (request.getGuideId() != null) {
            guideIds.add(request.getGuideId());
        } else {
            throw new RuntimeException("At least one guide ID must be provided");
        }
        
        // Generate multi-request ID if not provided and there are multiple guides
        String multiRequestId = request.getMultiRequestId();
        if (multiRequestId == null && guideIds.size() > 1) {
            multiRequestId = "MRQ-" + UUID.randomUUID().toString();
        }
        
        List<GuideBooking> createdBookings = new ArrayList<>();
        
        // Create a booking for each guide
        for (Long guideId : guideIds) {
            // Validate guide exists
            Guid guide = guidRepository.findById(guideId)
                .orElseThrow(() -> new RuntimeException("Guide not found with ID: " + guideId));
            
            // Check if guide is available for the requested dates
            Boolean isBooked = guideBookingRepository.isGuideBookedForDateRange(
                guide.getId(),
                request.getStartDate(),
                request.getEndDate()
            );
            
            if (Boolean.TRUE.equals(isBooked)) {
                // Skip this guide but continue with others
                continue;
            }
            
            // Create booking entity from DTO
            GuideBooking booking = new GuideBooking();
            booking.setGuide(guide);
            booking.setUser(user);
            booking.setLocations(request.getLocations());
            booking.setStartDate(request.getStartDate());
            booking.setEndDate(request.getEndDate());
            booking.setStartTime(request.getStartTime());
            booking.setNumberOfDays(request.getNumberOfDays());
            booking.setNumberOfPeople(request.getNumberOfPeople());
            booking.setContactNumber(request.getContactNumber());
            booking.setSpecialRequests(request.getSpecialRequests());
            booking.setPreferredLanguage(request.getPreferredLanguage());
            booking.setAccommodationNeeded(request.getAccommodationNeeded() != null ? request.getAccommodationNeeded() : false);
            booking.setTransportationNeeded(request.getTransportationNeeded() != null ? request.getTransportationNeeded() : false);
            booking.setMealPreferences(request.getMealPreferences());
            booking.setTotalPrice(request.getTotalPrice());
            booking.setMultiRequestId(multiRequestId);
            booking.setStatus("PENDING");
            booking.setPaymentStatus("PENDING");
            
            // Save all selected guide IDs as comma-separated string
            booking.setSelectedGuideIds(guideIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")));
            
            GuideBooking savedBooking = guideBookingRepository.save(booking);
            createdBookings.add(savedBooking);
            
            // Create corresponding guid_request entry
            createGuidRequest(savedBooking, guide, user);
        }
        
        if (createdBookings.isEmpty()) {
            throw new RuntimeException("No available guides found for the selected dates");
        }
        
        // Return the first booking (or primary guide's booking)
        return createdBookings.get(0);
    }
    
    /**
     * Create a guid_request entry for a guide booking
     */
    private void createGuidRequest(GuideBooking booking, Guid guide, User user) {
        GuidRequest guidRequest = new GuidRequest();
        guidRequest.setUser(user);
        guidRequest.setGuid(guide);
        guidRequest.setGuideBooking(booking);
        guidRequest.setBookingType("INDIVIDUAL");
        guidRequest.setStatus("PENDING");
        guidRequest.setStartDate(booking.getStartDate());
        guidRequest.setEndDate(booking.getEndDate());
        guidRequest.setNumberOfDays(booking.getNumberOfDays());
        guidRequest.setNumberOfPeople(booking.getNumberOfPeople());
        guidRequest.setLocations(booking.getLocations());
        guidRequest.setTotalPrice(booking.getTotalPrice());
        guidRequest.setMultiRequestId(booking.getMultiRequestId());
        guidRequest.setPaymentStatus("PENDING");
        
        guidRequestRepository.save(guidRequest);
    }


    /**
     * Get all bookings for a user
     */
    public List<GuideBooking> getMyBookings(Long userId) {
        return guideBookingRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    /**
     * Get all bookings for a user, grouped by multi_request_id
     * Returns a list of grouped bookings where each group contains all guides requested
     */
    public List<GroupedGuideBookingDTO> getMyBookingsGrouped(Long userId) {
        List<GuideBooking> allBookings = guideBookingRepository.findByUserIdOrderByCreatedAtDesc(userId);
        
        // Group by multi_request_id
        Map<String, List<GuideBooking>> groupedMap = new HashMap<>();
        
        for (GuideBooking booking : allBookings) {
            String key = booking.getMultiRequestId() != null ? booking.getMultiRequestId() : "single_" + booking.getId();
            groupedMap.computeIfAbsent(key, k -> new ArrayList<>()).add(booking);
        }
        
        // Convert to DTOs
        return groupedMap.values().stream()
            .map(GroupedGuideBookingDTO::fromBookings)
            .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt())) // Most recent first
            .collect(Collectors.toList());
    }

    /**
     * Get all booking requests for a guide
     */
    public List<GuideBooking> getGuideRequests(Long guideId) {
        return guideBookingRepository.findByGuideIdOrderByCreatedAtDesc(guideId);
    }

    /**
     * Get booking by ID
     */
    public Optional<GuideBooking> getBookingById(Long id) {
        return guideBookingRepository.findById(id);
    }

    /**
     * Guide approves a booking
     * If this is part of a multi-guide request, auto-cancel other pending requests
     * Sets the approved_guide_id and updates corresponding guid_request
     */
    @Transactional
    public GuideBooking approveBooking(Long bookingId, Long guideId) {
        GuideBooking booking = guideBookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        // Verify the guide owns this booking
        if (!booking.getGuide().getId().equals(guideId)) {
            throw new RuntimeException("Unauthorized: You can only approve your own bookings");
        }
        
        if (!"PENDING".equals(booking.getStatus())) {
            throw new RuntimeException("Only pending bookings can be approved");
        }
        
        booking.setStatus("APPROVED");
        booking.setApprovedAt(LocalDateTime.now());
        booking.setApprovedGuideId(guideId); // Track which guide approved
        
        GuideBooking savedBooking = guideBookingRepository.save(booking);
        
        // Update the corresponding guid_request
        updateGuidRequestStatus(booking, "APPROVED");
        
        // If this is part of a multi-guide request, cancel other pending bookings
        if (booking.getMultiRequestId() != null && !booking.getMultiRequestId().isEmpty()) {
            cancelOtherPendingBookingsInMultiRequest(booking.getMultiRequestId(), bookingId);
        }
        
        return savedBooking;
    }
    
    /**
     * Update guid_request status for a guide booking
     */
    private void updateGuidRequestStatus(GuideBooking booking, String status) {
        List<GuidRequest> guidRequests = guidRequestRepository.findByGuideBookingId(booking.getId());
        for (GuidRequest guidRequest : guidRequests) {
            guidRequest.setStatus(status);
            if ("PAID".equals(status)) {
                guidRequest.setPaymentStatus("PAID");
            }
            guidRequestRepository.save(guidRequest);
        }
    }
    
    /**
     * Cancel other pending bookings in the same multi-guide request
     * Also updates their corresponding guid_requests
     */
    @Transactional
    public void cancelOtherPendingBookingsInMultiRequest(String multiRequestId, Long approvedBookingId) {
        List<GuideBooking> relatedBookings = guideBookingRepository
            .findByMultiRequestIdAndStatus(multiRequestId, "PENDING");
        
        for (GuideBooking relatedBooking : relatedBookings) {
            if (!relatedBooking.getId().equals(approvedBookingId)) {
                relatedBooking.setStatus("CANCELLED");
                relatedBooking.setCancelledAt(LocalDateTime.now());
                relatedBooking.setCancellationReason("Another guide from the same request was approved first");
                guideBookingRepository.save(relatedBooking);
                
                // Update corresponding guid_request
                updateGuidRequestStatus(relatedBooking, "CANCELLED");
            }
        }
    }

    /**
     * Guide rejects a booking
     * Updates corresponding guid_request
     */
    @Transactional
    public GuideBooking rejectBooking(Long bookingId, Long guideId, String reason) {
        GuideBooking booking = guideBookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        // Verify the guide owns this booking
        if (!booking.getGuide().getId().equals(guideId)) {
            throw new RuntimeException("Unauthorized: You can only reject your own bookings");
        }
        
        if (!"PENDING".equals(booking.getStatus())) {
            throw new RuntimeException("Only pending bookings can be rejected");
        }
        
        booking.setStatus("REJECTED");
        booking.setRejectionReason(reason);
        booking.setUpdatedAt(LocalDateTime.now());
        
        GuideBooking savedBooking = guideBookingRepository.save(booking);
        
        // Update corresponding guid_request
        updateGuidRequestStatus(booking, "REJECTED");
        List<GuidRequest> guidRequests = guidRequestRepository.findByGuideBookingId(booking.getId());
        for (GuidRequest guidRequest : guidRequests) {
            guidRequest.setRejectionReason(reason);
            guidRequestRepository.save(guidRequest);
        }
        
        return savedBooking;
    }

    /**
     * User pays for an approved booking
     * Updates corresponding guid_request
     */
    @Transactional
    public GuideBooking payBooking(Long bookingId, Long userId) {
        GuideBooking booking = guideBookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        // Verify the user owns this booking
        if (!booking.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You can only pay for your own bookings");
        }
        
        if (!"APPROVED".equals(booking.getStatus())) {
            throw new RuntimeException("Only approved bookings can be paid");
        }
        
        // Check if payment is within 24 hours of approval
        if (booking.getApprovedAt() != null) {
            LocalDateTime deadline = booking.getApprovedAt().plusHours(24);
            if (LocalDateTime.now().isAfter(deadline)) {
                booking.setStatus("CANCELLED");
                booking.setCancelledAt(LocalDateTime.now());
                booking.setCancellationReason("Payment deadline expired");
                guideBookingRepository.save(booking);
                
                // Update guid_request
                updateGuidRequestStatus(booking, "CANCELLED");
                
                throw new RuntimeException("Payment deadline has expired. Booking has been cancelled.");
            }
        }
        
        booking.setStatus("PAID");
        booking.setPaymentStatus("PAID");
        booking.setPaidAt(LocalDateTime.now());
        
        GuideBooking savedBooking = guideBookingRepository.save(booking);
        
        // Update corresponding guid_request
        updateGuidRequestStatus(booking, "PAID");
        
        return savedBooking;
    }

    /**
     * Cancel a booking
     * Updates corresponding guid_request
     */
    @Transactional
    public GuideBooking cancelBooking(Long bookingId, Long userId, String reason) {
        GuideBooking booking = guideBookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        // Verify the user owns this booking
        if (!booking.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You can only cancel your own bookings");
        }
        
        if ("COMPLETED".equals(booking.getStatus()) || "CANCELLED".equals(booking.getStatus())) {
            throw new RuntimeException("Cannot cancel a completed or already cancelled booking");
        }
        
        booking.setStatus("CANCELLED");
        booking.setCancelledAt(LocalDateTime.now());
        booking.setCancellationReason(reason);
        
        GuideBooking savedBooking = guideBookingRepository.save(booking);
        
        // Update corresponding guid_request
        updateGuidRequestStatus(booking, "CANCELLED");
        
        return savedBooking;
    }

    /**
     * Complete a booking
     */
    @Transactional
    public GuideBooking completeBooking(Long bookingId, Long guideId) {
        GuideBooking booking = guideBookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        // Verify the guide owns this booking
        if (!booking.getGuide().getId().equals(guideId)) {
            throw new RuntimeException("Unauthorized: You can only complete your own bookings");
        }
        
        if (!"PAID".equals(booking.getStatus())) {
            throw new RuntimeException("Only paid bookings can be completed");
        }
        
        booking.setStatus("COMPLETED");
        booking.setCompletedAt(LocalDateTime.now());
        
        return guideBookingRepository.save(booking);
    }

    /**
     * Check guide availability for a date range
     */
    public boolean checkAvailability(Long guideId, LocalDate startDate, LocalDate endDate) {
        Boolean isBooked = guideBookingRepository.isGuideBookedForDateRange(guideId, startDate, endDate);
        return !Boolean.TRUE.equals(isBooked);
    }

    /**
     * Get bookings by status
     */
    public List<GuideBooking> getBookingsByStatus(String status) {
        return guideBookingRepository.findByStatus(status);
    }
}
