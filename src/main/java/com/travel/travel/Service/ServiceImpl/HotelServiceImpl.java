package com.travel.travel.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.travel.Models.Hotel;
import com.travel.travel.Repository.HotelRepository;
import com.travel.travel.Service.HotelService;

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

    @Override
    public Hotel updateHotel(Long id, Hotel hotel) throws Exception {
        Hotel existingHotel = hotelRepository.findById(id).orElse(null);
        if (existingHotel == null) {
            throw new Exception("Hotel not found");
        }
        // Use Lombok setters to update fields
        existingHotel.setHotelName(hotel.getHotelName());
        existingHotel.setStreet(hotel.getStreet());
        existingHotel.setCity(hotel.getCity());
        existingHotel.setDistrict(hotel.getDistrict());
        existingHotel.setProvince(hotel.getProvince());
        existingHotel.setRegistrationNo(hotel.getRegistrationNo());
        existingHotel.setLicensePhoto(hotel.getLicensePhoto());
        existingHotel.setImages(hotel.getImages());
        existingHotel.setType(hotel.getType());
        existingHotel.setDescription(hotel.getDescription());
        existingHotel.setAmenities(hotel.getAmenities());
        // Do not update createdAt or rooms here

        return hotelRepository.save(existingHotel);
    }
}