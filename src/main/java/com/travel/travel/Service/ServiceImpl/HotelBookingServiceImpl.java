package com.travel.travel.Service.ServiceImpl;

import com.travel.travel.DTO.HotelBookingRequest;
import com.travel.travel.DTO.HotelBookingResponse;
import com.travel.travel.Models.Hotel;
import com.travel.travel.Models.HotelBooking;
import com.travel.travel.Models.Room;
import com.travel.travel.Models.User;
import com.travel.travel.Repository.HotelBookingRepository;
import com.travel.travel.Repository.HotelRepository;
import com.travel.travel.Repository.RoomRepository;
import com.travel.travel.Repository.UserRepository;
import com.travel.travel.Service.HotelBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelBookingServiceImpl implements HotelBookingService {
    
    private final HotelBookingRepository hotelBookingRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    
    @Override
    @Transactional
    public HotelBooking createBooking(HotelBookingRequest request) throws Exception {
        // Validate user
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new Exception("User not found"));
        
        // Validate hotel
        Hotel hotel = hotelRepository.findById(request.getHotelId())
            .orElseThrow(() -> new Exception("Hotel not found"));
        
        // Validate room
        Room room = roomRepository.findById(request.getRoomId())
            .orElseThrow(() -> new Exception("Room not found"));
        
        // Check if room belongs to hotel
        if (!room.getHotel().getId().equals(hotel.getId())) {
            throw new Exception("Room does not belong to the selected hotel");
        }
        
        // Check room availability
        if (!room.getAvailability()) {
            throw new Exception("Room is not available");
        }
        
        // Calculate number of nights
        long nights = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        if (nights <= 0) {
            throw new Exception("Check-out date must be after check-in date");
        }
        
        // Calculate total amount
        BigDecimal pricePerNight = room.getPricePerNight() != null ? 
            BigDecimal.valueOf(room.getPricePerNight()) : BigDecimal.ZERO;
        BigDecimal totalAmount = pricePerNight.multiply(BigDecimal.valueOf(nights));
        
        // Create booking
        HotelBooking booking = new HotelBooking();
        booking.setUser(user);
        booking.setUserEmail(request.getUserEmail());
        booking.setUserName(request.getUserName());
        booking.setUserPhone(request.getUserPhone());
        
        booking.setHotel(hotel);
        booking.setHotelName(hotel.getHotelName());
        booking.setRoom(room);
        booking.setRoomType(room.getRoomType());
        
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setNumberOfNights((int) nights);
        booking.setNumberOfGuests(request.getNumberOfGuests());
        
        booking.setPricePerNight(pricePerNight);
        booking.setTotalAmount(totalAmount);
        booking.setCurrency("LKR");
        
        booking.setPaymentStatus("PENDING");
        booking.setBookingStatus("PENDING");
        booking.setSpecialRequests(request.getSpecialRequests());
        
        if (request.getStripePaymentIntentId() != null) {
            booking.setStripePaymentIntentId(request.getStripePaymentIntentId());
        }
        
        return hotelBookingRepository.save(booking);
    }
    
    @Override
    public HotelBooking getBookingById(Long id) {
        return hotelBookingRepository.findById(id).orElse(null);
    }
    
    @Override
    public HotelBooking getBookingByReference(String reference) {
        return hotelBookingRepository.findByBookingReference(reference).orElse(null);
    }
    
    @Override
    public List<HotelBooking> getBookingsByUserId(Long userId) {
        return hotelBookingRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    
    @Override
    public List<HotelBooking> getBookingsByHotelId(Long hotelId) {
        return hotelBookingRepository.findByHotelId(hotelId);
    }
    
    @Override
    @Transactional
    public HotelBooking confirmPayment(String paymentIntentId) throws Exception {
        HotelBooking booking = hotelBookingRepository.findByStripePaymentIntentId(paymentIntentId)
            .orElseThrow(() -> new Exception("Booking not found for payment intent"));
        
        booking.setPaymentStatus("COMPLETED");
        booking.setBookingStatus("CONFIRMED");
        booking.setPaymentDate(LocalDateTime.now());
        booking.setPaymentMethod("STRIPE");
        
        return hotelBookingRepository.save(booking);
    }
    
    @Override
    @Transactional
    public HotelBooking cancelBooking(Long bookingId, String reason) throws Exception {
        HotelBooking booking = hotelBookingRepository.findById(bookingId)
            .orElseThrow(() -> new Exception("Booking not found"));
        
        if ("CANCELLED".equals(booking.getBookingStatus())) {
            throw new Exception("Booking is already cancelled");
        }
        
        booking.setBookingStatus("CANCELLED");
        booking.setCancelledAt(LocalDateTime.now());
        booking.setCancellationReason(reason);
        
        return hotelBookingRepository.save(booking);
    }
    
    @Override
    public HotelBookingResponse toResponse(HotelBooking booking) {
        HotelBookingResponse response = new HotelBookingResponse();
        response.setId(booking.getId());
        response.setBookingReference(booking.getBookingReference());
        response.setUserEmail(booking.getUserEmail());
        response.setUserName(booking.getUserName());
        response.setUserPhone(booking.getUserPhone());
        response.setHotelId(booking.getHotel().getId());
        response.setHotelName(booking.getHotelName());
        response.setRoomId(booking.getRoom().getId());
        response.setRoomType(booking.getRoomType());
        response.setCheckInDate(booking.getCheckInDate());
        response.setCheckOutDate(booking.getCheckOutDate());
        response.setNumberOfNights(booking.getNumberOfNights());
        response.setNumberOfGuests(booking.getNumberOfGuests());
        response.setPricePerNight(booking.getPricePerNight());
        response.setTotalAmount(booking.getTotalAmount());
        response.setCurrency(booking.getCurrency());
        response.setPaymentStatus(booking.getPaymentStatus());
        response.setBookingStatus(booking.getBookingStatus());
        response.setSpecialRequests(booking.getSpecialRequests());
        response.setCreatedAt(booking.getCreatedAt());
        
        return response;
    }
}
