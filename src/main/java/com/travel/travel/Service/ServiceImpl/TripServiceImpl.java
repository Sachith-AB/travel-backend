package com.travel.travel.Service.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.travel.Models.Hotel;
import com.travel.travel.Models.Room;
import com.travel.travel.Models.Trip;
import com.travel.travel.Repository.GuidRepository;
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

    @Override
    public Trip createTrip(Trip trip) throws Exception {
        
        if (trip.getUser() != null && trip.getUser().getId() != null) {
            trip.setUser(userRepository.findById(trip.getUser().getId())
                .orElseThrow(() -> new Exception("User not found")));
        }
        if (trip.getSelectedVehicle() != null && trip.getSelectedVehicle().getId() != null) {
            trip.setSelectedVehicle(vehicleRepository.findById(trip.getSelectedVehicle().getId())
                .orElseThrow(() -> new Exception("Vehicle not found")));
        }
        if (trip.getSelectedVehicleAgency() != null && trip.getSelectedVehicleAgency().getId() != null) {
            trip.setSelectedVehicleAgency(vehicleAgencyRepository.findById(trip.getSelectedVehicleAgency().getId())
                .orElseThrow(() -> new Exception("Vehicle Agency not found")));
        }
        if (trip.getSelectedHotels() != null && !trip.getSelectedHotels().isEmpty()) {
            List<Hotel> managedHotels = new ArrayList<>();
            for (Hotel hotel : trip.getSelectedHotels()) {
                managedHotels.add(hotelRepository.findById(hotel.getId())
                    .orElseThrow(() -> new Exception("Hotel not found")));
            }
            trip.setSelectedHotels(managedHotels);
        }
        if (trip.getSelectedRooms() != null && !trip.getSelectedRooms().isEmpty()) {
            List<Room> managedRooms = new ArrayList<>();
            for (Room room : trip.getSelectedRooms()) {
                managedRooms.add(roomRepository.findById(room.getId())
                    .orElseThrow(() -> new Exception("Room not found")));
            }
            trip.setSelectedRooms(managedRooms);
        }
        return tripRepository.save(trip);
    }

	@Override
	public Optional<Trip> tripGetById(Long id) throws Exception {
		return tripRepository.findById(id);
	}

	@Override
	public Trip updateTrip(Long id, Trip tripUpdate) throws Exception {
		Trip existingTrip = tripRepository.findById(id)
			.orElseThrow(() -> new Exception("Trip not found with id: " + id));

		// Update selected guide if provided
		if (tripUpdate.getSelectedGuide() != null && tripUpdate.getSelectedGuide().getId() != null) {
			existingTrip.setSelectedGuide(guidRepository.findById(tripUpdate.getSelectedGuide().getId())
				.orElseThrow(() -> new Exception("Guide not found")));
		}

		// Update trip status if provided
		if (tripUpdate.getTripStatus() != null) {
			existingTrip.setTripStatus(tripUpdate.getTripStatus());
		}

		// Update user if provided
		if (tripUpdate.getUser() != null && tripUpdate.getUser().getId() != null) {
			existingTrip.setUser(userRepository.findById(tripUpdate.getUser().getId())
				.orElseThrow(() -> new Exception("User not found")));
		}

		// Update vehicle if provided
		if (tripUpdate.getSelectedVehicle() != null && tripUpdate.getSelectedVehicle().getId() != null) {
			existingTrip.setSelectedVehicle(vehicleRepository.findById(tripUpdate.getSelectedVehicle().getId())
				.orElseThrow(() -> new Exception("Vehicle not found")));
		}

		// Update vehicle agency if provided
		if (tripUpdate.getSelectedVehicleAgency() != null && tripUpdate.getSelectedVehicleAgency().getId() != null) {
			existingTrip.setSelectedVehicleAgency(vehicleAgencyRepository.findById(tripUpdate.getSelectedVehicleAgency().getId())
				.orElseThrow(() -> new Exception("Vehicle Agency not found")));
		}

		// Update hotels if provided
		if (tripUpdate.getSelectedHotels() != null && !tripUpdate.getSelectedHotels().isEmpty()) {
			List<Hotel> managedHotels = new ArrayList<>();
			for (Hotel hotel : tripUpdate.getSelectedHotels()) {
				managedHotels.add(hotelRepository.findById(hotel.getId())
					.orElseThrow(() -> new Exception("Hotel not found")));
			}
			existingTrip.setSelectedHotels(managedHotels);
		}

		// Update rooms if provided
		if (tripUpdate.getSelectedRooms() != null && !tripUpdate.getSelectedRooms().isEmpty()) {
			List<Room> managedRooms = new ArrayList<>();
			for (Room room : tripUpdate.getSelectedRooms()) {
				managedRooms.add(roomRepository.findById(room.getId())
					.orElseThrow(() -> new Exception("Room not found")));
			}
			existingTrip.setSelectedRooms(managedRooms);
		}

		// Update other fields if needed
		if (tripUpdate.getPickupLocation() != null) {
			existingTrip.setPickupLocation(tripUpdate.getPickupLocation());
		}
		if (tripUpdate.getTripStartDate() != null) {
			existingTrip.setTripStartDate(tripUpdate.getTripStartDate());
		}
		if (tripUpdate.getTripEndDate() != null) {
			existingTrip.setTripEndDate(tripUpdate.getTripEndDate());
		}
		if (tripUpdate.getBasePrice() != null) {
			existingTrip.setBasePrice(tripUpdate.getBasePrice());
		}
		if (tripUpdate.getTotalFare() != null) {
			existingTrip.setTotalFare(tripUpdate.getTotalFare());
		}

		return tripRepository.save(existingTrip);
	}
}
