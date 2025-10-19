package com.travel.travel.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.travel.travel.Models.GuideBooking;

@Repository
public interface GuideBookingRepository extends JpaRepository<GuideBooking, Long> {
    
    // Find all bookings for a specific user
    List<GuideBooking> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    // Find all booking requests for a specific guide
    List<GuideBooking> findByGuideIdOrderByCreatedAtDesc(Long guideId);
    
    // Find bookings by status
    List<GuideBooking> findByStatus(String status);
    
    // Find bookings by user and status
    List<GuideBooking> findByUserIdAndStatus(Long userId, String status);
    
    // Find bookings by guide and status
    List<GuideBooking> findByGuideIdAndStatus(Long guideId, String status);
    
    // Find bookings by multiRequestId and status
    List<GuideBooking> findByMultiRequestIdAndStatus(String multiRequestId, String status);
    
    // Check if guide is available for a date range
    @Query("SELECT COUNT(gb) > 0 FROM GuideBooking gb WHERE gb.guide.id = :guideId " +
           "AND gb.status IN ('APPROVED', 'PAID') " +
           "AND ((gb.startDate <= :endDate AND gb.endDate >= :startDate))")
    Boolean isGuideBookedForDateRange(
        @Param("guideId") Long guideId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
    
    // Find pending bookings that need payment
    @Query("SELECT gb FROM GuideBooking gb WHERE gb.status = 'APPROVED' " +
           "AND gb.paymentStatus = 'PENDING' " +
           "AND gb.approvedAt <= :cutoffTime " +
           "ORDER BY gb.approvedAt ASC")
    List<GuideBooking> findPendingPayments(@Param("cutoffTime") LocalDateTime cutoffTime);
}
