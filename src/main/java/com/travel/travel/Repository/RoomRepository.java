package com.travel.travel.Repository;

import com.travel.travel.Models.Room;
import com.travel.travel.Models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotel(Hotel hotel);
}