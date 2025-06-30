package com.travel.travel.Service.ServiceImpl;

import com.travel.travel.Models.Trip;
import com.travel.travel.Repository.TripRepository;
import com.travel.travel.Service.TripService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    TripRepository tripRepository;

    @Override
    public Trip createTrip(Trip trip) throws Exception {
        return tripRepository.save(trip);
    }

	@Override
	public Optional<Trip> tripGetById(Long id) throws Exception {
		return tripRepository.findById(id);
	}
}
