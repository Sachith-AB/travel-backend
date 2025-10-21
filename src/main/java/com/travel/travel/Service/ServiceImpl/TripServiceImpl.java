package com.travel.travel.Service.ServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        
      
        if (trip.getUser() != null && trip.getUser().getId() != null) {
            trip.setUser(userRepository.findById(trip.getUser().getId())
                .orElseThrow(() -> new Exception("User not found")));
        }
        
        
        if (trip.getSelectedVehicle() != null && trip.getSelectedVehicle().getId() != null) {
            trip.setSelectedVehicle(vehicleRepository.findById(trip.getSelectedVehicle().getId())
                .orElseThrow(() -> new Exception("Vehicle not found")));
                
           
            if (trip.getSelectedVehicleAgency() == null || trip.getSelectedVehicleAgency().getId() == null) {
                if (trip.getSelectedVehicle().getAgency() != null) {
                    trip.setSelectedVehicleAgency(trip.getSelectedVehicle().getAgency());
                }
            }
        }
        
       
        if (trip.getSelectedVehicleAgency() != null && trip.getSelectedVehicleAgency().getId() != null) {
            trip.setSelectedVehicleAgency(vehicleAgencyRepository.findById(trip.getSelectedVehicleAgency().getId())
                .orElseThrow(() -> new Exception("Vehicle Agency not found")));
        }
        
       
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
        
       
        Trip savedTrip = tripRepository.save(trip);
        
       
        System.out.println("=== SAVED TRIP DEBUG ===");
        System.out.println("Trip ID: " + savedTrip.getId());
        System.out.println("Trip Code: " + savedTrip.getTripCode());
        System.out.println("Trip Start Date: " + savedTrip.getTripStartDate());
        System.out.println("Trip End Date: " + savedTrip.getTripEndDate());
        System.out.println("Number of Adults: " + savedTrip.getNumberOfAdults());
        System.out.println("Number of Kids: " + savedTrip.getNumberOfKids());
        System.out.println("Pickup Location: " + savedTrip.getPickupLocation());
        System.out.println("Total Fare: " + savedTrip.getTotalFare());
        System.out.println("Selected Guide IDs: " + savedTrip.getSelectedGuideIds());
        System.out.println("========================");
        
        
        if (trip.getSelectedGuideIds() != null && !trip.getSelectedGuideIds().isEmpty()) {
            
            String multiRequestId = "TOUR-" + savedTrip.getTripCode();
            
            System.out.println("Creating guide requests for " + trip.getSelectedGuideIds().size() + " guides");
            System.out.println("Multi-Request ID: " + multiRequestId);
            
            
            String travelerName = savedTrip.getFullName();
            String travelerEmail = savedTrip.getEmail();
            
            System.out.println("Traveler Name: " + travelerName);
            System.out.println("Traveler Email: " + travelerEmail);
            
            for (Long guideId : trip.getSelectedGuideIds()) {
                Guid guide = guidRepository.findById(guideId)
                    .orElseThrow(() -> new Exception("Guide not found with ID: " + guideId));
                
                GuidRequest guidRequest = new GuidRequest();
                
                
                guidRequest.setUser(savedTrip.getUser());
                guidRequest.setTrip(savedTrip);
                guidRequest.setGuid(guide);
                guidRequest.setStatus("PENDING");
                
             
                guidRequest.setBookingType("TOUR");
                guidRequest.setMultiRequestId(multiRequestId);
                
               
                if (savedTrip.getTripStartDate() != null) {
                    LocalDate startDate = convertToLocalDate(savedTrip.getTripStartDate());
                    guidRequest.setStartDate(startDate);
                    System.out.println("  Set start_date: " + startDate);
                } else {
                    System.out.println("   WARNING: Trip start date is NULL!");
                }
                
                if (savedTrip.getTripEndDate() != null) {
                    LocalDate endDate = convertToLocalDate(savedTrip.getTripEndDate());
                    guidRequest.setEndDate(endDate);
                    System.out.println("  Set end_date: " + endDate);
                } else {
                    System.out.println("   WARNING: Trip end date is NULL!");
                }
                
                
                if (savedTrip.getTripStartDate() != null && savedTrip.getTripEndDate() != null) {
                    LocalDate startLocal = convertToLocalDate(savedTrip.getTripStartDate());
                    LocalDate endLocal = convertToLocalDate(savedTrip.getTripEndDate());
                    int days = (int) ChronoUnit.DAYS.between(startLocal, endLocal) + 1;
                    guidRequest.setNumberOfDays(days);
                    System.out.println("  Set number_of_days: " + days);
                } else {
                    System.out.println("  ⚠️ WARNING: Cannot calculate days - dates are NULL!");
                }
                
                int totalPeople = savedTrip.getNumberOfAdults() + savedTrip.getNumberOfKids();
                guidRequest.setNumberOfPeople(totalPeople);
                System.out.println("  Set number_of_people: " + totalPeople);
                
                guidRequest.setLocations(savedTrip.getPickupLocation());
                System.out.println("  Set locations: " + savedTrip.getPickupLocation());
                
                
                guidRequest.setTravelerName(travelerName);
                guidRequest.setTravelerEmail(travelerEmail);
                guidRequest.setRequestDate(LocalDateTime.now());
                System.out.println("  Set traveler_name: " + travelerName);
                System.out.println("  Set traveler_email: " + travelerEmail);
                System.out.println("  Set request_date: " + guidRequest.getRequestDate());
                
                
                Double guidePrice = 0.0;
                if (guide.getHoursRate() != null && guidRequest.getNumberOfDays() != null) {
                    
                    double hoursPerDay = 8.0;
                    guidePrice = guide.getHoursRate() * hoursPerDay * guidRequest.getNumberOfDays();
                    System.out.println("  Guide hourly rate: " + guide.getHoursRate());
                    System.out.println("  Days: " + guidRequest.getNumberOfDays());
                    System.out.println("  Calculated guide price: " + guidePrice + " (rate: " + guide.getHoursRate() + " × " + hoursPerDay + " hours/day × " + guidRequest.getNumberOfDays() + " days)");
                } else {
                   
                    if (savedTrip.getTotalFare() != null) {
                        guidePrice = savedTrip.getTotalFare().doubleValue();
                        System.out.println("  ⚠️ Guide rate not available, using trip total fare: " + guidePrice);
                    } else {
                        System.out.println("  ⚠️ WARNING: Both guide rate and total fare are NULL!");
                    }
                }
                guidRequest.setTotalPrice(guidePrice);
                System.out.println("  Set total_price: " + guidePrice);
                
                guidRequest.setPaymentStatus("PENDING");
                
               
                System.out.println("=== GUIDE REQUEST BEFORE SAVE (Guide ID: " + guideId + ") ===");
                System.out.println("  booking_type: " + guidRequest.getBookingType());
                System.out.println("  multi_request_id: " + guidRequest.getMultiRequestId());
                System.out.println("  traveler_name: " + guidRequest.getTravelerName());
                System.out.println("  traveler_email: " + guidRequest.getTravelerEmail());
                System.out.println("  request_date: " + guidRequest.getRequestDate());
                System.out.println("  start_date: " + guidRequest.getStartDate());
                System.out.println("  end_date: " + guidRequest.getEndDate());
                System.out.println("  number_of_days: " + guidRequest.getNumberOfDays());
                System.out.println("  number_of_people: " + guidRequest.getNumberOfPeople());
                System.out.println("  locations: " + guidRequest.getLocations());
                System.out.println("  total_price: " + guidRequest.getTotalPrice());
                System.out.println("  payment_status: " + guidRequest.getPaymentStatus());
                System.out.println("========================================");
                
                GuidRequest savedRequest = guidRequestRepository.save(guidRequest);
                
               
                System.out.println("=== GUIDE REQUEST AFTER SAVE (ID: " + savedRequest.getId() + ") ===");
                System.out.println("  booking_type: " + savedRequest.getBookingType());
                System.out.println("  multi_request_id: " + savedRequest.getMultiRequestId());
                System.out.println("  traveler_name: " + savedRequest.getTravelerName());
                System.out.println("  traveler_email: " + savedRequest.getTravelerEmail());
                System.out.println("  request_date: " + savedRequest.getRequestDate());
                System.out.println("  start_date: " + savedRequest.getStartDate());
                System.out.println("  end_date: " + savedRequest.getEndDate());
                System.out.println("  number_of_days: " + savedRequest.getNumberOfDays());
                System.out.println("  number_of_people: " + savedRequest.getNumberOfPeople());
                System.out.println("  locations: " + savedRequest.getLocations());
                System.out.println("  total_price: " + savedRequest.getTotalPrice());
                System.out.println("  payment_status: " + savedRequest.getPaymentStatus());
                System.out.println("=========================================");
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
		return tripRepository.findByUserIdOrderByCreatedAtDesc(userId);
	}
	
	@Override
	public Trip updateTripStatus(Long tripId, String status) throws Exception {
		Trip trip = tripRepository.findById(tripId)
			.orElseThrow(() -> new Exception("Trip not found with ID: " + tripId));
		
		
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

	
	private LocalDate convertToLocalDate(java.sql.Date date) {
		if (date == null) {
			return null;
		}
		return date.toLocalDate();
	}

	@Override
	public Trip updateTrip(Long id, Trip tripUpdate) throws Exception {
		Trip existingTrip = tripRepository.findById(id)
			.orElseThrow(() -> new Exception("Trip not found with id: " + id));

		
		if (tripUpdate.getSelectedGuide() != null && tripUpdate.getSelectedGuide().getId() != null) {
			existingTrip.setSelectedGuide(guidRepository.findById(tripUpdate.getSelectedGuide().getId())
				.orElseThrow(() -> new Exception("Guide not found")));
		}

		
		if (tripUpdate.getTripStatus() != null) {
			existingTrip.setTripStatus(tripUpdate.getTripStatus());
		}

		
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

		
		if (tripUpdate.getSelectedHotels() != null && !tripUpdate.getSelectedHotels().isEmpty()) {
			List<Hotel> managedHotels = new ArrayList<>();
			for (Hotel hotel : tripUpdate.getSelectedHotels()) {
				managedHotels.add(hotelRepository.findById(hotel.getId())
					.orElseThrow(() -> new Exception("Hotel not found")));
			}
			existingTrip.setSelectedHotels(managedHotels);
		}

		
		if (tripUpdate.getSelectedRooms() != null && !tripUpdate.getSelectedRooms().isEmpty()) {
			List<Room> managedRooms = new ArrayList<>();
			for (Room room : tripUpdate.getSelectedRooms()) {
				managedRooms.add(roomRepository.findById(room.getId())
					.orElseThrow(() -> new Exception("Room not found")));
			}
			existingTrip.setSelectedRooms(managedRooms);
		}

		
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
