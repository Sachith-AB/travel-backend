package com.travel.travel.Repository;

import com.travel.travel.Models.VehicleBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleBookingRepository extends JpaRepository<VehicleBooking, Long> {
    
    List<VehicleBooking> findByUserId(Long userId);
    
    List<VehicleBooking> findByVehicleId(Long vehicleId);
    
    List<VehicleBooking> findByBookingStatus(String bookingStatus);
    
    List<VehicleBooking> findByPaymentStatus(String paymentStatus);
    
    Optional<VehicleBooking> findByPaymentIntentId(String paymentIntentId);
}
