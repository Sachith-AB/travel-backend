package com.travel.travel.DTOs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.travel.travel.Models.GuideBooking;

import lombok.Data;

/**
 * DTO for displaying grouped guide bookings
 * Groups multiple guide bookings by multi_request_id
 * Shows all guides requested and their individual statuses
 */
@Data
public class GroupedGuideBookingDTO {
    private String multiRequestId;
    private Long userId;
    private String userName;
    private String locations;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private Integer numberOfDays;
    private Integer numberOfPeople;
    private String contactNumber;
    private String specialRequests;
    private String preferredLanguage;
    private Boolean accommodationNeeded;
    private Boolean transportationNeeded;
    private String mealPreferences;
    private LocalDateTime createdAt;
    
    // List of all guides requested for this booking
    private List<GuideBookingItem> guides;
    
    // Overall booking status
    private String overallStatus; // PENDING (all pending), APPROVED (one approved), CANCELLED, PAID, COMPLETED
    
    // The guide who was approved (if any)
    private Long approvedGuideId;
    private String approvedGuideName;
    
    /**
     * Inner class representing each guide in the booking
     */
    @Data
    public static class GuideBookingItem {
        private Long bookingId;
        private Long guideId;
        private String guideName;
        private String guideImage;
        private String guideLanguages;
        private Double guidePrice;
        private Double totalPrice;
        private String status; // PENDING, APPROVED, REJECTED, CANCELLED, PAID, COMPLETED
        private String rejectionReason;
        private LocalDateTime approvedAt;
        private LocalDateTime rejectedAt;
        private LocalDateTime paidAt;
        private LocalDateTime completedAt;
        private LocalDateTime cancelledAt;
        
        // Construct from GuideBooking entity
        public static GuideBookingItem fromGuideBooking(GuideBooking booking) {
            GuideBookingItem item = new GuideBookingItem();
            item.setBookingId(booking.getId());
            item.setGuideId(booking.getGuide().getId());
            
            // Build guide name from user
            if (booking.getGuide().getUser() != null) {
                String firstName = booking.getGuide().getUser().getFirstName() != null ? 
                    booking.getGuide().getUser().getFirstName() : "";
                String lastName = booking.getGuide().getUser().getLastName() != null ? 
                    booking.getGuide().getUser().getLastName() : "";
                item.setGuideName((firstName + " " + lastName).trim());
            } else {
                item.setGuideName("Unknown Guide");
            }
            
            // Note: Guide languages are stored in a separate table, would need join query to fetch
            // For now, leaving it null
            
            // Get guide price
            item.setGuidePrice(booking.getGuide().getHoursRate());
            item.setTotalPrice(booking.getTotalPrice());
            item.setStatus(booking.getStatus());
            item.setRejectionReason(booking.getRejectionReason());
            item.setApprovedAt(booking.getApprovedAt());
            item.setPaidAt(booking.getPaidAt());
            item.setCompletedAt(booking.getCompletedAt());
            item.setCancelledAt(booking.getCancelledAt());
            
            return item;
        }
    }
    
    /**
     * Create a grouped booking DTO from a list of related bookings
     */
    public static GroupedGuideBookingDTO fromBookings(List<GuideBooking> bookings) {
        if (bookings == null || bookings.isEmpty()) {
            return null;
        }
        
        // Use the first booking as the template (they all share common booking details)
        GuideBooking firstBooking = bookings.get(0);
        
        GroupedGuideBookingDTO dto = new GroupedGuideBookingDTO();
        dto.setMultiRequestId(firstBooking.getMultiRequestId());
        dto.setUserId(firstBooking.getUser().getId());
        
        // Build user name
        String firstName = firstBooking.getUser().getFirstName() != null ? 
            firstBooking.getUser().getFirstName() : "";
        String lastName = firstBooking.getUser().getLastName() != null ? 
            firstBooking.getUser().getLastName() : "";
        dto.setUserName((firstName + " " + lastName).trim());
        
        dto.setLocations(firstBooking.getLocations());
        dto.setStartDate(firstBooking.getStartDate());
        dto.setEndDate(firstBooking.getEndDate());
        dto.setStartTime(firstBooking.getStartTime());
        dto.setNumberOfDays(firstBooking.getNumberOfDays());
        dto.setNumberOfPeople(firstBooking.getNumberOfPeople());
        dto.setContactNumber(firstBooking.getContactNumber());
        dto.setSpecialRequests(firstBooking.getSpecialRequests());
        dto.setPreferredLanguage(firstBooking.getPreferredLanguage());
        dto.setAccommodationNeeded(firstBooking.getAccommodationNeeded());
        dto.setTransportationNeeded(firstBooking.getTransportationNeeded());
        dto.setMealPreferences(firstBooking.getMealPreferences());
        dto.setCreatedAt(firstBooking.getCreatedAt());
        
        // Convert all bookings to GuideBookingItems
        List<GuideBookingItem> guideItems = bookings.stream()
            .map(GuideBookingItem::fromGuideBooking)
            .toList();
        dto.setGuides(guideItems);
        
        // Determine overall status
        boolean hasApproved = bookings.stream().anyMatch(b -> "APPROVED".equals(b.getStatus()) || "PAID".equals(b.getStatus()) || "COMPLETED".equals(b.getStatus()));
        boolean allCancelled = bookings.stream().allMatch(b -> "CANCELLED".equals(b.getStatus()));
        boolean hasPaid = bookings.stream().anyMatch(b -> "PAID".equals(b.getStatus()) || "COMPLETED".equals(b.getStatus()));
        boolean hasCompleted = bookings.stream().anyMatch(b -> "COMPLETED".equals(b.getStatus()));
        
        if (hasCompleted) {
            dto.setOverallStatus("COMPLETED");
        } else if (hasPaid) {
            dto.setOverallStatus("PAID");
        } else if (hasApproved) {
            dto.setOverallStatus("APPROVED");
            // Find and set the approved guide
            bookings.stream()
                .filter(b -> "APPROVED".equals(b.getStatus()) || "PAID".equals(b.getStatus()) || "COMPLETED".equals(b.getStatus()))
                .findFirst()
                .ifPresent(b -> {
                    dto.setApprovedGuideId(b.getApprovedGuideId());
                    if (b.getGuide().getUser() != null) {
                        String fn = b.getGuide().getUser().getFirstName() != null ? 
                            b.getGuide().getUser().getFirstName() : "";
                        String ln = b.getGuide().getUser().getLastName() != null ? 
                            b.getGuide().getUser().getLastName() : "";
                        dto.setApprovedGuideName((fn + " " + ln).trim());
                    }
                });
        } else if (allCancelled) {
            dto.setOverallStatus("CANCELLED");
        } else {
            dto.setOverallStatus("PENDING");
        }
        
        return dto;
    }
}
