package com.travel.travel.Service;

import com.travel.travel.Models.Hotel;

public interface HotelService {
    Hotel registerHotel(Hotel hotel) throws Exception;

    Hotel getHotelById(Long id);
}