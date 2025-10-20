package com.travel.travel.Service;

import com.travel.travel.DTO.VehicleBookingRequest;
import com.travel.travel.Models.User;
import com.travel.travel.Models.Vehicle;
import com.travel.travel.Models.VehicleBooking;
import com.travel.travel.Repository.UserRepository;
import com.travel.travel.Repository.VehicleBookingRepository;
import com.travel.travel.Repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleBookingService {
    
    private final VehicleBookingRepository vehicleBookingRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    
    @Transactional
    public VehicleBooking createBooking(VehicleBookingRequest request) {
        // Validate vehicle exists
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
            .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + request.getVehicleId()));
        
        // Validate user exists
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));
        
        // Create booking
        VehicleBooking booking = new VehicleBooking();
        booking.setVehicle(vehicle);
        booking.setUser(user);
        
        // Set booking details
        booking.setPickupDate(request.getPickupDate());
        booking.setPickupTime(parseTime(request.getPickupTime()));
        booking.setReturnDate(request.getReturnDate());
        booking.setReturnTime(parseTime(request.getReturnTime()));
        booking.setPickupLocation(request.getPickupLocation());
        booking.setDropoffLocation(request.getDropoffLocation());
        
        // Set driver options
        booking.setWithDriver(request.getWithDriver() != null ? request.getWithDriver() : false);
        booking.setDriverLicenseNumber(request.getDriverLicenseNumber());
        booking.setLicenseExpiryDate(request.getLicenseExpiryDate());
        
        // Set pricing
        booking.setBasePrice(request.getBasePrice());
        booking.setDriverFee(request.getDriverFee());
        booking.setTotalCost(request.getTotalCost());
        booking.setNumberOfDays(request.getNumberOfDays());
        
        // Set payment information
        booking.setPaymentIntentId(request.getPaymentIntentId());
        booking.setPaymentStatus(request.getPaymentStatus() != null ? request.getPaymentStatus() : "PENDING");
        booking.setPaymentMethod(request.getPaymentMethod());
        booking.setPaymentDate(request.getPaymentDate());
        
        // Set booking status
        booking.setBookingStatus(request.getBookingStatus() != null ? request.getBookingStatus() : "PENDING");
        
        // Set contact information
        booking.setFirstName(request.getFirstName());
        booking.setLastName(request.getLastName());
        booking.setEmail(request.getEmail());
        booking.setPhone(request.getPhone());
        
        // Set additional information
        booking.setSpecialRequests(request.getSpecialRequests());
        
        return vehicleBookingRepository.save(booking);
    }
    
    public VehicleBooking getBookingById(Long id) {
        return vehicleBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }
    
    public List<VehicleBooking> getBookingsByUserId(Long userId) {
        return vehicleBookingRepository.findByUserId(userId);
    }
    
    public List<VehicleBooking> getBookingsByVehicleId(Long vehicleId) {
        return vehicleBookingRepository.findByVehicleId(vehicleId);
    }
    
    @Transactional
    public VehicleBooking updateBooking(VehicleBooking booking) {
        return vehicleBookingRepository.save(booking);
    }
    
    @Transactional
    public void cancelBooking(Long id, String reason) {
        VehicleBooking booking = getBookingById(id);
        booking.setBookingStatus("CANCELLED");
        booking.setCancellationReason(reason);
        booking.setCancelledAt(java.time.LocalDateTime.now());
        vehicleBookingRepository.save(booking);
    }
    
    private LocalTime parseTime(String timeString) {
        if (timeString == null || timeString.isEmpty()) {
            return LocalTime.of(10, 0); // Default to 10:00
        }
        try {
            return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (Exception e) {
            return LocalTime.of(10, 0); // Default to 10:00 if parsing fails
        }
    }
}
