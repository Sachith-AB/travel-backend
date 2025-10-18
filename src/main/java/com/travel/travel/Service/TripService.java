package com.travel.travel.Service;

import java.util.List;
import java.util.Optional;

import com.travel.travel.Models.Trip;

public interface TripService {
    Trip createTrip(Trip trip) throws Exception;

    Optional<Trip> tripGetById(Long id) throws Exception;
    
    List<Trip> getTripsByUserId(Long userId) throws Exception;
}
