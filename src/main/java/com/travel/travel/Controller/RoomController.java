package com.travel.travel.Controller;

import com.travel.travel.Models.Hotel;
import com.travel.travel.Models.Room;
import com.travel.travel.Models.User;
import com.travel.travel.Service.HotelService;
import com.travel.travel.Service.RoomService;
import com.travel.travel.Service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelService hotelService;
    
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createRoom(
            @RequestBody Room room, 
            @RequestHeader("X-User-DocId") String userDocId) {
        try {
            // Get user by Firebase UID (docId)
            Optional<User> userOpt = userService.findByPublicId(userDocId);
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(401).body("User not authenticated");
            }
            
            // Get hotel associated with this user
            Optional<Hotel> hotelOpt = hotelService.getHotelByUserId(userOpt.get().getId());
            if (hotelOpt.isEmpty()) {
                return ResponseEntity.status(404).body("No hotel found for this user");
            }
            
            Hotel hotel = hotelOpt.get();
            
            // Check if hotel is verified by admin
            if (hotel.getIsVerified() == null || !hotel.getIsVerified()) {
                return ResponseEntity.status(403).body("Your hotel must be verified by admin before adding rooms");
            }
            
            // Set the hotel for the room
            room.setHotel(hotel);
            
            // Create the room
            Room savedRoom = roomService.createRoom(room);
            return ResponseEntity.ok(savedRoom);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id);
        if (room == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(room);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Room>> getRoomsByHotelId(@PathVariable Long hotelId) {
        return ResponseEntity.ok(roomService.getRoomsByHotelId(hotelId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable Long id, @RequestBody Room room) {
        Room updated = roomService.updateRoom(id, room);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok().build();
    }
}