package com.travel.travel.Service;

import com.travel.travel.Models.Room;
import java.util.List;

public interface RoomService {
    Room createRoom(Room room);
    List<Room> getAllRooms();
    Room getRoomById(Long id);
    List<Room> getRoomsByHotelId(Long hotelId);
    Room updateRoom(Long id, Room room);
    void deleteRoom(Long id);
}