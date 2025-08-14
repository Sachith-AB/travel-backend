package com.travel.travel.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.travel.Repository.GuidRepository;
import com.travel.travel.Repository.HotelRepository;
import com.travel.travel.Repository.VehicleRepository;
import com.travel.travel.RequestDto.GuidSearchDTO;
import com.travel.travel.RequestDto.HotelSearchDTO;
import com.travel.travel.RequestDto.VehicleSearchDTO;

@Service
public class SearchService {

    @Autowired
    private GuidRepository guidRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<GuidSearchDTO> getAvailableGuides() {
        return guidRepository.findAvailableVerifiedGuides();
    }

    public List<HotelSearchDTO> getHotelsByCity(String city) {
        return hotelRepository.findHotelsByCity(city);
    }

    public List<VehicleSearchDTO> getVehiclesByCapacity(Integer capacity) {
        return vehicleRepository.findVehiclesByMinCapacity(capacity);
    }
}
