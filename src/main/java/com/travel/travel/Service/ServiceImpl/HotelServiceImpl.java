package com.travel.travel.Service.ServiceImpl;

import com.travel.travel.Models.Hotels;
import com.travel.travel.Repository.HotelRepository;
import com.travel.travel.Service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {
    @Override
    public Optional<Hotels> findById(Long id) {
        return hotelRepository.findById(id);
    }

    @Autowired
    private HotelRepository hotelRepository;
    @Override
    public Hotels createHotel(Hotels hotels) {
        return hotelRepository.save(hotels);
    }
}
