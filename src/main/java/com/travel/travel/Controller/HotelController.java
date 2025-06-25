package com.travel.travel.Controller;

import com.travel.travel.Models.Hotel;
import com.travel.travel.Service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/register")
    public ResponseEntity<?> registerHotel(@RequestBody Hotel hotel) {
        try {
            Hotel savedHotel = hotelService.registerHotel(hotel);
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
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
}