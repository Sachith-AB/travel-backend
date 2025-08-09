package com.travel.travel.Service;

import java.util.List;

import com.travel.travel.Models.Hotel;

public interface HotelService {
    Hotel registerHotel(Hotel hotel) throws Exception;

    Hotel getHotelById(Long id);

    Hotel updateHotel(Long id, Hotel hotel) throws Exception;

    List<Hotel> getAllHotels();
}