package com.travel.travel.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.travel.Models.Hotel;
import com.travel.travel.Models.User;
import com.travel.travel.Service.HotelService;
import com.travel.travel.Service.UserService;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerHotel(@RequestBody Hotel hotel) {
        try {
            // Save the hotel
            Hotel savedHotel = hotelService.registerHotel(hotel);
            
            // Update user role to HOTEL_OWNER if user exists
            if (savedHotel.getUser() != null && savedHotel.getUser().getId() != null) {
                Optional<User> userOpt = userService.getUserById(savedHotel.getUser().getId());
                if (userOpt.isPresent()) {
                    User user = userOpt.get();
                    user.setRole("HOTEL_OWNER");
                    userService.updateUser(user, user.getId());
                }
            }
            
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/{hotelId}")
    public ResponseEntity<?> getHotelWithRooms(@PathVariable Long hotelId) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        if (hotel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hotel);
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<?> updateHotel(@PathVariable Long hotelId, @RequestBody Hotel hotel) {
        try {
            Hotel updatedHotel = hotelService.updateHotel(hotelId, hotel);
            return ResponseEntity.ok(updatedHotel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }
    
    @GetMapping("/user/{docId}")
    public ResponseEntity<?> getHotelByUserDocId(@PathVariable String docId) {
        try {
            Optional<User> userOpt = userService.findByPublicId(docId);
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(404).body("User not found");
            }
            
            Optional<Hotel> hotelOpt = hotelService.getHotelByUserId(userOpt.get().getId());
            if (hotelOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Hotel not found for this user");
            }
            
            return ResponseEntity.ok(hotelOpt.get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}