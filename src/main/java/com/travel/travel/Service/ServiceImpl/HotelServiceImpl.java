package com.travel.travel.Service.ServiceImpl;

import com.travel.travel.Models.Hotel;
import com.travel.travel.Repository.HotelRepository;
import com.travel.travel.Service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel registerHotel(Hotel hotel) throws Exception {
        return hotelRepository.save(hotel);
    }


    @Override
    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).orElse(null);
    }
}