package com.travel.travel.Service;

import com.travel.travel.Models.Hotel;
import java.util.List;
import java.util.Optional;

public interface HotelService {
    Hotel registerHotel(Hotel hotel) throws Exception;

    Hotel getHotelById(Long id);

    Hotel updateHotel(Long id, Hotel hotel) throws Exception;

    List<Hotel> getAllHotels();
    
    Optional<Hotel> getHotelByUserId(Long userId);
}