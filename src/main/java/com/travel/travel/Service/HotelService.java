package com.travel.travel.Service;

import com.travel.travel.Models.Hotels;

import java.util.Optional;

public interface HotelService {
    Hotels createHotel(Hotels hotels);

    Optional<Hotels> findById(Long id);
}
