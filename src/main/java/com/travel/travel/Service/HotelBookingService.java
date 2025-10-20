package com.travel.travel.Service;

import com.travel.travel.DTO.HotelBookingRequest;
import com.travel.travel.DTO.HotelBookingResponse;
import com.travel.travel.Models.HotelBooking;
import java.util.List;

public interface HotelBookingService {
    
    /**
     * Create a new hotel booking
     * @param request The booking request details
     * @return The created booking
     */
    HotelBooking createBooking(HotelBookingRequest request) throws Exception;
    
    /**
     * Update an existing booking
     * @param booking The booking to update
     * @return The updated booking
     */
    HotelBooking updateBooking(HotelBooking booking);
    
    /**
     * Get booking by ID
     * @param id The booking ID
     * @return The booking
     */
    HotelBooking getBookingById(Long id);
    
    /**
     * Get booking by reference
     * @param reference The booking reference
     * @return The booking
     */
    HotelBooking getBookingByReference(String reference);
    
    /**
     * Get all bookings for a user
     * @param userId The user ID
     * @return List of bookings
     */
    List<HotelBooking> getBookingsByUserId(Long userId);
    
    /**
     * Get all bookings for a hotel
     * @param hotelId The hotel ID
     * @return List of bookings
     */
    List<HotelBooking> getBookingsByHotelId(Long hotelId);
    
    /**
     * Confirm booking payment
     * @param paymentIntentId The Stripe payment intent ID
     * @return Updated booking
     */
    HotelBooking confirmPayment(String paymentIntentId) throws Exception;
    
    /**
     * Cancel a booking
     * @param bookingId The booking ID
     * @param reason The cancellation reason
     * @return Updated booking
     */
    HotelBooking cancelBooking(Long bookingId, String reason) throws Exception;
    
    /**
     * Convert entity to response DTO
     * @param booking The booking entity
     * @return The response DTO
     */
    HotelBookingResponse toResponse(HotelBooking booking);
}
