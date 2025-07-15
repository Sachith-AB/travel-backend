package com.travel.travel.Service.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.travel.Models.Room;
import com.travel.travel.Models.Trip;
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
        if (trip.getSelectedHotel() != null && trip.getSelectedHotel().getId() != null) {
            trip.setSelectedHotel(hotelRepository.findById(trip.getSelectedHotel().getId())
                .orElseThrow(() -> new Exception("Hotel not found")));
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
}
