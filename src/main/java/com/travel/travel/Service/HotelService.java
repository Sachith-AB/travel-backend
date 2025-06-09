package com.travel.travel.Service;

import com.travel.travel.Models.Hotel;

import java.util.Optional;

public interface HotelService {
    Hotel createHotel(Hotel hotels);

    Optional<Hotel> findById(Long id);
}
