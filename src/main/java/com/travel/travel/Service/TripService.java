package com.travel.travel.Service;

import java.util.Optional;

import com.travel.travel.Models.Trip;

public interface TripService {
    Trip createTrip(Trip trip) throws Exception;

    Optional<Trip> tripGetById(Long id) throws Exception;

    Trip updateTrip(Long id, Trip trip) throws Exception;
}
