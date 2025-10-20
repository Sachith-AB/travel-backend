package com.travel.travel.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.travel.Models.HotelBooking;

@Repository
public interface HotelBookingRepository extends JpaRepository<HotelBooking, Long> {
    
    Optional<HotelBooking> findByBookingReference(String bookingReference);
    
    List<HotelBooking> findByUserId(Long userId);
    
    List<HotelBooking> findByHotelId(Long hotelId);
    
    List<HotelBooking> findByRoomId(Long roomId);
    
    List<HotelBooking> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    List<HotelBooking> findByBookingStatus(String bookingStatus);
    
    List<HotelBooking> findByPaymentStatus(String paymentStatus);
    
    Optional<HotelBooking> findByStripePaymentIntentId(String stripePaymentIntentId);
}
