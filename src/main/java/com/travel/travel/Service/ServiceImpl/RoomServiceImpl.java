package com.travel.travel.Service.ServiceImpl;

import com.travel.travel.Models.Room;
import com.travel.travel.Models.Hotel;
import com.travel.travel.Repository.RoomRepository;
import com.travel.travel.Repository.HotelRepository;
import com.travel.travel.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Room createRoom(Room room) {
        room.setCreatedAt(LocalDateTime.now());
        return roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public List<Room> getRoomsByHotelId(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);
        if (hotel == null) return List.of();
        return roomRepository.findByHotel(hotel);
    }

    @Override
    public Room updateRoom(Long id, Room room) {
        Optional<Room> existing = roomRepository.findById(id);
        if (existing.isPresent()) {
            Room r = existing.get();
            r.setRoomType(room.getRoomType());
            r.setPricePerNight(room.getPricePerNight());
            r.setImages(room.getImages());
            r.setAmenities(room.getAmenities());
            r.setAvailability(room.getAvailability());
            // Do not update createdAt or id/hotel
            return roomRepository.save(r);
        }
        return null;
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}