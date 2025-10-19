package com.travel.travel.Service.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.travel.Models.Guid;
import com.travel.travel.Models.GuidRequest;
import com.travel.travel.Models.Hotel;
import com.travel.travel.Models.Room;
import com.travel.travel.Models.Trip;
import com.travel.travel.Repository.GuidRepository;
import com.travel.travel.Repository.GuidRequestRepository;
import com.travel.travel.Repository.HotelRepository;
import com.travel.travel.Repository.RoomRepository;
import com.travel.travel.Repository.TripRepository;
import com.travel.travel.Repository.UserRepository;
import com.travel.travel.Repository.VehicleAgencyRepository;
import com.travel.travel.Repository.VehicleRepository;
import com.travel.travel.Service.TripService;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private VehicleAgencyRepository vehicleAgencyRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private GuidRepository guidRepository;
    @Autowired
    private GuidRequestRepository guidRequestRepository;

    @Override
    public Trip createTrip(Trip trip) throws Exception {
        
        // Handle user
        if (trip.getUser() != null && trip.getUser().getId() != null) {
            trip.setUser(userRepository.findById(trip.getUser().getId())
                .orElseThrow(() -> new Exception("User not found")));
        }
        
        // Handle vehicle
        if (trip.getSelectedVehicle() != null && trip.getSelectedVehicle().getId() != null) {
            trip.setSelectedVehicle(vehicleRepository.findById(trip.getSelectedVehicle().getId())
                .orElseThrow(() -> new Exception("Vehicle not found")));
                
            // Auto-set vehicle agency from the vehicle if not explicitly provided
            if (trip.getSelectedVehicleAgency() == null || trip.getSelectedVehicleAgency().getId() == null) {
                if (trip.getSelectedVehicle().getAgency() != null) {
                    trip.setSelectedVehicleAgency(trip.getSelectedVehicle().getAgency());
                }
            }
        }
        
        // Handle vehicle agency (if explicitly provided)
        if (trip.getSelectedVehicleAgency() != null && trip.getSelectedVehicleAgency().getId() != null) {
            trip.setSelectedVehicleAgency(vehicleAgencyRepository.findById(trip.getSelectedVehicleAgency().getId())
                .orElseThrow(() -> new Exception("Vehicle Agency not found")));
        }
        
        // Handle hotels
        if (trip.getSelectedHotels() != null && !trip.getSelectedHotels().isEmpty()) {
            List<Hotel> managedHotels = new ArrayList<>();
            for (Hotel hotel : trip.getSelectedHotels()) {
                if (hotel != null && hotel.getId() != null) {
                    managedHotels.add(hotelRepository.findById(hotel.getId())
                        .orElseThrow(() -> new Exception("Hotel not found with ID: " + hotel.getId())));
                }
            }
            trip.setSelectedHotels(managedHotels);
        }
        
        // Handle rooms
        if (trip.getSelectedRooms() != null && !trip.getSelectedRooms().isEmpty()) {
            List<Room> managedRooms = new ArrayList<>();
            for (Room room : trip.getSelectedRooms()) {
                if (room != null && room.getId() != null) {
                    managedRooms.add(roomRepository.findById(room.getId())
                        .orElseThrow(() -> new Exception("Room not found with ID: " + room.getId())));
                }
            }
            trip.setSelectedRooms(managedRooms);
        }
        
        // Set display fields for easy database viewing
        if (trip.getSelectedGuideIds() != null && !trip.getSelectedGuideIds().isEmpty()) {
            trip.setGuidesDisplay(trip.getSelectedGuideIds().stream()
                .map(String::valueOf)
                .reduce((a, b) -> a + "," + b)
                .orElse(""));
        }
        
        if (trip.getSelectedHotelIds() != null && !trip.getSelectedHotelIds().isEmpty()) {
            trip.setSelectedHotelIdsDisplay(trip.getSelectedHotelIds().stream()
                .map(String::valueOf)
                .reduce((a, b) -> a + "," + b)
                .orElse(""));
        }
        
        // Save the trip first
        Trip savedTrip = tripRepository.save(trip);
        
        // Create guide requests for all selected guides
        if (trip.getSelectedGuideIds() != null && !trip.getSelectedGuideIds().isEmpty()) {
            for (Long guideId : trip.getSelectedGuideIds()) {
                Guid guide = guidRepository.findById(guideId)
                    .orElseThrow(() -> new Exception("Guide not found with ID: " + guideId));
                
                GuidRequest guidRequest = new GuidRequest();
                guidRequest.setUser(savedTrip.getUser());
                guidRequest.setTrip(savedTrip);
                guidRequest.setGuid(guide);
                guidRequest.setStatus("pending");
                
                guidRequestRepository.save(guidRequest);
            }
        }
        
        return savedTrip;
    }

	@Override
	public Optional<Trip> tripGetById(Long id) throws Exception {
		return tripRepository.findById(id);
	}

	@Override
	public List<Trip> getTripsByUserId(Long userId) throws Exception {
		return tripRepository.findByUserId(userId);
	}
	
	@Override
	public Trip updateTripStatus(Long tripId, String status) throws Exception {
		Trip trip = tripRepository.findById(tripId)
			.orElseThrow(() -> new Exception("Trip not found with ID: " + tripId));
		
		// Validate status
		if (!isValidStatus(status)) {
			throw new Exception("Invalid trip status: " + status);
		}
		
		trip.setTripStatus(com.travel.travel.Models.Enum.TripStatus.valueOf(status));
		return tripRepository.save(trip);
	}
	
	private boolean isValidStatus(String status) {
		try {
			com.travel.travel.Models.Enum.TripStatus.valueOf(status);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
