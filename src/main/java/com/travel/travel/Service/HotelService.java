package com.travel.travel.Service;

import com.travel.travel.Models.Hotel;
import com.travel.travel.Models.Room;

public interface HotelService {
    Hotel registerHotel(Hotel hotel) throws Exception;

    Hotel getHotelById(Long id);

    Hotel updateHotel(Long id, Hotel hotel) throws Exception;
}