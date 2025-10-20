package com.travel.travel.Service.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.travel.Models.Enum.TripStatus;
import com.travel.travel.Models.GuidRequest;
import com.travel.travel.Models.Trip;
import com.travel.travel.Repository.GuidRequestRepository;
import com.travel.travel.Repository.TripRepository;
import com.travel.travel.Service.GuidRequestService;

@Service
public class GuidRequestServiceImpl implements GuidRequestService {

    @Autowired
    private GuidRequestRepository guidRequestRepository;
    
    @Autowired
    private TripRepository tripRepository;

    @Override
    public GuidRequest createRequest(GuidRequest request) throws Exception {
        request.setStatus("pending");
        return guidRequestRepository.save(request);
    }

    @Override
    public GuidRequest updateRequest(Long id, GuidRequest request) throws Exception {
        GuidRequest existing = guidRequestRepository.findById(id)
                .orElseThrow(() -> new Exception("Request not found"));
        
        String newStatus = request.getStatus();
        String oldStatus = existing.getStatus();
        
        existing.setStatus(newStatus);
        
        // If guide request is approved or accepted, update the trip and recalculate total fare
        if (newStatus != null && 
            (newStatus.equalsIgnoreCase("approved") || newStatus.equalsIgnoreCase("accepted")) &&
            !newStatus.equalsIgnoreCase(oldStatus)) {
            
            Trip trip = existing.getTrip();
            if (trip != null) {
                boolean tripUpdated = false;
                
                // Only update if trip is still pending
                if (trip.getTripStatus() == TripStatus.pending) {
                    trip.setTripStatus(TripStatus.accepted);
                    tripUpdated = true;
                    System.out.println("✅ Auto-updated trip " + trip.getId() + " status to 'accepted' after guide approval");
                }
                
                // Set the approved guide as the selected guide
                if (existing.getGuid() != null && trip.getSelectedGuide() == null) {
                    trip.setSelectedGuide(existing.getGuid());
                    tripUpdated = true;
                    System.out.println("✅ Set guide " + existing.getGuid().getId() + " as selected guide for trip " + trip.getId());
                }
                
                // Recalculate total fare to include the accepted guide's price
                if (existing.getTotalPrice() != null && existing.getTotalPrice() > 0) {
                    // Get current total fare (base price from trip)
                    double currentTotal = trip.getTotalFare() != null ? trip.getTotalFare().doubleValue() : 0.0;
                    
                    // Get base price (trip cost without guide)
                    double basePrice = trip.getBasePrice() != null ? trip.getBasePrice().doubleValue() : 0.0;
                    
                    // Calculate new total: base price + accepted guide's price
                    double guidePrice = existing.getTotalPrice();
                    double newTotalFare = basePrice + guidePrice;
                    
                    // Only update if different
                    if (Math.abs(currentTotal - newTotalFare) > 0.01) {
                        trip.setTotalFare(java.math.BigDecimal.valueOf(newTotalFare));
                        tripUpdated = true;
                        System.out.println("✅ Updated trip " + trip.getId() + " total fare: LKR " + 
                            String.format("%.2f", newTotalFare) + 
                            " (Base: " + String.format("%.2f", basePrice) + 
                            " + Guide: " + String.format("%.2f", guidePrice) + ")");
                    }
                }
                
                // Save trip if any changes were made
                if (tripUpdated) {
                    tripRepository.save(trip);
                }
            }
        }
        
        // Optionally update other fields if needed
        return guidRequestRepository.save(existing);
    }

    @Override
    public void deleteRequest(Long id) throws Exception {
        if (!guidRequestRepository.existsById(id)) {
            throw new Exception("Request not found");
        }
        guidRequestRepository.deleteById(id);
    }

    @Override
    public List<GuidRequest> getAllRequests() {
        return guidRequestRepository.findAll();
    }

    @Override
    public List<GuidRequest> getRequestsByTripId(Long tripId) {
        return guidRequestRepository.findByTripId(tripId);
    }

    @Override
    public List<GuidRequest> getRequestsByUserId(Long userId) {
        return guidRequestRepository.findByUserId(userId);
    }

    @Override
    public List<GuidRequest> getRequestsByGuidId(Long guidId) {
        return guidRequestRepository.findByGuidId(guidId);
    }

    @Override
    public GuidRequest getById(Long id) {
        return guidRequestRepository.findById(id).orElse(null);
    }
}